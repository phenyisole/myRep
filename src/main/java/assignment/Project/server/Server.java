package assignment.Project.server;

import assignment.Project.database.*;
import assignment.Project.msgclass.Messagepackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
public class Server {
	final String ALL="ALL";
	final String REMOVE="REMOVE";
	final String HEART="HEART";
	final String USER="USER";
	final String PRIVATE="PRIVATE";
	final String REPEAT="REPEAT";
	final String ADMISSION="ADMISSION";
	final String DENIAL="DENIAL";

	int port;
	private Timer timer;
	private TimerTask task;
	ServerUI serUI;
	ServerSocket sd;
	public Map<String, Socket> sockets = new ConcurrentHashMap<>();
	public Map<PriUser, Socket> prisockets = new ConcurrentHashMap<>();
	boolean running = true;

	public void handleDoc(Socket socket,Messagepackage doc) {
		new Thread(() -> {
			if(doc.getBesendedUser().equals(ALL)&&!doc.isPri()) {
				for(Socket socketDoc:sockets.values()) {

					ObjectOutputStream os = null;
					if(!socketDoc.isClosed())
						try {
							os = new ObjectOutputStream(socketDoc.getOutputStream());
							os.writeObject(doc);

						} catch (IOException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
				}
			}else {
				ObjectOutputStream os = null;
//    				System.out.println(new String(doc.getDocByte()));
				if(!socket.isClosed())
					try {
						handleBroadcast(sockets.get(doc.getBesendedUser()), PRIVATE, false, doc.getSendUser());
						Thread.sleep(100);
						os = new ObjectOutputStream(find(doc).getOutputStream());
						os.writeObject(doc);
						os.flush();
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
			}


		}).start();
	}
	public Socket find(Messagepackage doc) {
		for (Entry<PriUser, Socket> entry : prisockets.entrySet()) {
			PriUser key = entry.getKey();
			if((key.getSendUser()+key.getBeSendUser()).equals(doc.getBesendedUser()+doc.getSendUser()))
				return entry.getValue();
		}
		return null;
	}

	public void handleMsg(Socket socket,Messagepackage doc) {
		new Thread(() -> {
			ObjectOutputStream os = null;
			if(doc.isPri()) {
				if(!socket.isClosed())
					try {
						handleBroadcast(sockets.get(doc.getBesendedUser()), PRIVATE, false, doc.getSendUser());
						Thread.sleep(100);
						os = new ObjectOutputStream(find(doc).getOutputStream());
						os.writeObject(doc);
						os.flush();
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
//    				e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
			}else {

				for(Socket socketDoc:sockets.values()) {
					if(!socketDoc.isClosed())
						try {
							os = new ObjectOutputStream(socketDoc.getOutputStream());
							os.writeObject(doc);
							os.flush();
						} catch (IOException e) {
							// TODO 自动生成的 catch 块
//						e.printStackTrace();
						}
				}
			}

		}).start();
	}
	public void handleBroadcast(Socket socket,String s,boolean isALL,String sendUser) {
		Messagepackage doc = new Messagepackage();
		ObjectOutputStream os = null;
		doc.setServer(true);
		doc.setProblem(s);
		doc.setSendUser(sendUser);
		if(!isALL) {
			if(!socket.isClosed())
				try {
					os = new ObjectOutputStream(socket.getOutputStream());
					os.writeObject(doc);
					os.flush();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
//    			e.printStackTrace();
				}

		}else {

			for(Socket sockets:sockets.values()) {
				if(!sockets.isClosed())
					try {
						os = new ObjectOutputStream(sockets.getOutputStream());
						os.writeObject(doc);
						os.flush();
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						try {
							sockets.close();
						} catch (IOException e1) {
							// TODO 自动生成的 catch 块
							e1.printStackTrace();
						}
//					e.printStackTrace();
					}
			}
		}

	}
	public void handleServer(Socket socket,Messagepackage doc) {

		if(doc.isForgetPass()) {
			Messagepackage redoc = new Messagepackage();
			ObjectOutputStream os = null;
			redoc=PasswordReminder.check(doc.getUser());
			if(!socket.isClosed())
				try {
					Thread.sleep(50);
					os = new ObjectOutputStream(socket.getOutputStream());
					os.writeObject(redoc);
					os.flush();
					socket.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					//    	    			e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
		}else
		if(doc.isLogin()) {
			Messagepackage redoc = new Messagepackage();
			ObjectOutputStream os = null;
			redoc.setProblem(DENIAL);
			if(doc.isRegister()) {
				if(RegisterAccount.check(doc.getUser(), doc.getPassword(),doc.getQuestion(),doc.getAnswer()))
					redoc.setProblem(ADMISSION);
			}else {
				if(LoginCheck.check(doc.getUser(), doc.getPassword()))
					redoc.setProblem(ADMISSION);
			}
			if(!socket.isClosed())
				try {
					Thread.sleep(50);
					os = new ObjectOutputStream(socket.getOutputStream());
					os.writeObject(redoc);
					os.flush();
					socket.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					//    	    			e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}

		}else {
			serUI.model.removeElement(doc.getSendUser());
			sockets.remove(doc.getSendUser());
			try {
				handleBroadcast(null, REMOVE, true, doc.getSendUser());
				sockets.get(doc.getSendUser()).close();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}

	}
	public void startServer() throws IOException {
		sd =new ServerSocket(port);
		new Thread(() -> {

			while (running) {
				try {
					Socket server=sd.accept();
					new Thread(() -> {
						boolean enter=false;
						ObjectInputStream in = null;
						Messagepackage doc=null;
						try {
							in = new ObjectInputStream(server.getInputStream());
							doc = (Messagepackage) in.readObject();
						} catch (IOException | ClassNotFoundException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
						if(doc.isForgetPass()) {
							handleServer(server, doc);
						}else
						if(doc.isLogin()) {
							handleServer(server, doc);
						}else {
							if(doc.isPri()){

								prisockets.put(new PriUser(doc.getSendUser(), doc.getBesendedUser()), server);
								enter=true;
								//							handleBroadcast(sockets.get(doc.getBesendedUser()), PRIVATE, false, doc.getSendUser());
							}else {
								if(sockets.containsKey(doc.getSendUser())) {
									handleBroadcast(server, REPEAT,false,doc.getSendUser());
								}else {
									sockets.put(doc.getSendUser(), server);
									serUI.model.addElement(doc.getSendUser());
									int itemCount = serUI.users.getModel().getSize();
									serUI.onlineNum.setText("在线人数："+itemCount);
									enter=true;
								}

							}
						}
						if(enter) {
							while (!server.isClosed()) {
								try {
									in = new ObjectInputStream(server.getInputStream());
									if(in!=null)
										doc = (Messagepackage) in.readObject();
								} catch (IOException | ClassNotFoundException e) {
									// TODO 自动生成的 catch 块
									//									e.printStackTrace();
								}
								if(doc.isServer()) {
									handleServer(server,doc);
								}
								if(doc!=null) {
									if(doc.isDoc()) {
										handleDoc(server, doc);
									}else {
										handleMsg(server, doc);
									}
								}

							}}
					}).start();
				} catch (IOException e) {
					if (sd.isClosed()) {
						// Ignore the exception if the ServerSocket is closed.
						break;
					}
					e.printStackTrace();
				}
			}
		}).start();
	}
	public void heartBeat() {
		timer = new Timer();
		task = new TimerTask() {
			Messagepackage doc = new Messagepackage();
			ObjectOutputStream os = null;

			@Override
			public void run() {
				for (String name : sockets.keySet()) {
					if (sockets.get(name).isClosed()) {
						// 处理已关闭的Socket，例如从相关的数据结构中移除
						sockets.remove(name);
						serUI.model.removeElement(name);
						int itemCount = serUI.users.getModel().getSize();
						serUI.onlineNum.setText("在线人数：" + itemCount);
						handleBroadcast(sockets.get(name), REMOVE, true, name);
						continue; // 跳过已关闭的Socket
					}

					handleBroadcast(sockets.get(name), USER, true, name);
					doc.setServer(true);
					doc.setProblem(HEART);
					try {
						os = new ObjectOutputStream(sockets.get(name).getOutputStream());
						os.writeObject(doc);
						os.flush();
					} catch (Exception e) {
						sockets.remove(name);
						serUI.model.removeElement(name);
						int itemCount = serUI.users.getModel().getSize();
						serUI.onlineNum.setText("在线人数：" + itemCount);
						handleBroadcast(sockets.get(name), REMOVE, true, name);
						continue; // 跳过已关闭的Socket
					}
				}
			}
		};
		timer.schedule(task, 0, 100);
	}


	public void stopServer() {
		try {

			for (Socket socket : sockets.values()) {
				socket.close();
			}

			for (Socket socket : prisockets.values()) {
				socket.close();
			}
			sd.close();

			sockets.clear();

			prisockets.clear();
		} catch (IOException e) {
//            e.printStackTrace();
		}

		running = false;
	}

	public Server(int port,ServerUI serUI) {

		this.port = port;
		this.serUI=serUI;
		heartBeat();
	}
}