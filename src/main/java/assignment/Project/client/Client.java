package assignment.Project.client;

import assignment.Project.database.RecordStorage;
import assignment.Project.function.MusicPlayer;
import assignment.Project.function.ShakeWin;
import assignment.Project.msgclass.Messagepackage;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Client {
	final String ALL="ALL";
	final String REMOVE="REMOVE";
	final String HEART="HEART";
	final String USER="USER";
	final String PRIVATE="PRIVATE";
	final String REPEAT="REPEAT";

	Socket client;
	InputStream is;
	OutputStream os;
	FileOutputStream fos;
	FileInputStream fis;
	public boolean isPri;
	String ip;
	int port;
	String user;
	ClientUI clientUI;
	PrivateUI privateUI;
	public boolean monitor=true,isFirst=true;

	public Client(String ip,int port,ClientUI clientUI,String user,boolean pri) {
		super();
		// TODO 自动生成的构造函数存根
		this.isPri=pri;
		this.ip=ip;
		this.user=user;
		this.port=port;
		this.clientUI=clientUI;
		if(pri) {
			privateUI=(PrivateUI)clientUI;
		}
		try {
			client=new Socket(ip,port);
			if(pri)
				output("NULL",user,privateUI.user,false);
			else
				output("NULL",user,"NULL",false);
			isFirst=false;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(clientUI, "连接失败","提示消息",JOptionPane.WARNING_MESSAGE);
			monitor=false;
		}
	}
	public void input(Messagepackage doc) {

		new Thread(() -> {
			if(!doc.getSendUser().equals(user)){
				new Thread(()->{
					new MusicPlayer("src\\main\\java\\music.beep\\msgBeep.wav");
				}).start();
			}
			try {
				if(isPri) {
					privateUI.docs.insertString(privateUI.docs.getLength(), doc.getSendUser()+"  ", privateUI.style3);
					privateUI.docs.insertString(privateUI.docs.getLength(), doc.getTime()+"\n", privateUI.style);
					if(doc.getSendUser().equals(user)) {
						privateUI.docs.insertString(privateUI.docs.getLength(),"  "+new String(doc.getDocByte()) + "\n", privateUI.style2);
					}else
						privateUI.docs.insertString(privateUI.docs.getLength(),"  "+new String(doc.getDocByte()) + "\n", null);
					Thread.sleep(100);
					JScrollBar verticalBar = privateUI.scrollPane2.getVerticalScrollBar();
					verticalBar.setValue(verticalBar.getMaximum());
				}else {
					clientUI.docs.insertString(clientUI.docs.getLength(), doc.getSendUser()+"  ", clientUI.style3);
					clientUI.docs.insertString(clientUI.docs.getLength(), doc.getTime()+"\n", clientUI.style);
					if(doc.getSendUser().equals(user)) {
						clientUI.docs.insertString(clientUI.docs.getLength(),"  "+new String(doc.getDocByte()) + "\n", clientUI.style2);
					}else
						clientUI.docs.insertString(clientUI.docs.getLength(),"  "+new String(doc.getDocByte()) + "\n", null);
					Thread.sleep(100);
					JScrollBar verticalBar = clientUI.scrollPane2.getVerticalScrollBar();
					verticalBar.setValue(verticalBar.getMaximum());
					RecordStorage.RecordStorages(doc.getSendUser(), new String(doc.getDocByte()), doc.getTime());
				}
			} catch (BadLocationException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}

		}).start();
	}
	public void inputCorrect(Messagepackage doc) {
		if(doc.getProblem().equals(REPEAT)) {
			JOptionPane.showMessageDialog(clientUI, "用户名重复了哦","提示消息",JOptionPane.WARNING_MESSAGE);
			clientUI.login.setEnabled(true);
			clientUI.isLogin=false;
			clientUI.userName.setEditable(true);
		}else if(doc.getProblem().equals(USER)) {
			SwingUtilities.invokeLater(() -> {
				if (!clientUI.model.contains(doc.getSendUser())) {
					clientUI.model.addElement(doc.getSendUser());
				}
			});

		}else if(doc.getProblem().equals(HEART)) {}
		else if(doc.getProblem().equals(REMOVE)) {
			SwingUtilities.invokeLater(() -> {
				clientUI.model.removeElement(doc.getSendUser());
			});
		}else if(doc.getProblem().equals(PRIVATE)) {
			if(clientUI.plist.containsKey(doc.getSendUser()))
				clientUI.plist.get(doc.getSendUser()).setVisible(true);
			else
				clientUI.plist.put(doc.getSendUser(), new PrivateUI(clientUI.port.getText(),clientUI.ip.getText(),doc.getSendUser(),user));
		}


	}
	public void startShutdownHook() throws IOException {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			stop();
		}));
	}
	public void inputDivide(){
		new Thread(() -> {
			ObjectInputStream in = null;
			Messagepackage doc=null;

			while(!client.isClosed()) {
				try {
					in = new ObjectInputStream(client.getInputStream());
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					clientUI.quit.doClick();
				}
				if(in!=null) {
					try {
						doc = (Messagepackage) in.readObject();
					} catch (IOException | ClassNotFoundException e) {
						// TODO 自动生成的 catch 块
//						e.printStackTrace();
					}
					if (doc != null) {
						if(doc.isServer()) {
							inputCorrect(doc);
						}else {
							if(doc.isShake()) {
								try {
									if(isPri) {
										ShakeWin.shakeWindow(privateUI);
										privateUI.docs.insertString(privateUI.docs.getLength(), "                                                       "+doc.getSendUser()+"发来一个窗口抖动"+"\n", privateUI.style);
									}else {
										ShakeWin.shakeWindow(clientUI);
										clientUI.docs.insertString(clientUI.docs.getLength(), "                                                       "+doc.getSendUser()+"发来一个窗口抖动"+"\n", clientUI.style);
									}
								} catch (InterruptedException e) {
									// TODO 自动生成的 catch 块
									e.printStackTrace();
								} catch (BadLocationException e) {
									// TODO 自动生成的 catch 块
									e.printStackTrace();
								}
							}else {
								if(doc.isVideo()){
									new VideoAcceptUI(doc.getSendUser(),doc.getVideoPort(),privateUI);

								}else
									if(doc.isDoc()) {
										inputDoc(doc);
									}else {
										input(doc);
									}
							}
						}
					}


				}
			}
		}).start();
	}
	public void outputDoc(String path,String sendUser,String beSendUser) throws IOException {
		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String formattedDateTime = currentDateTime.format(formatter);
		File file = new File(path);
		byte[] buffer = new byte[(int) file.length()];
		fis=new FileInputStream(file);
		fis.read(buffer);
		String [] s=path.split("\\\\");
		String text=s[s.length-1];
		ObjectOutputStream os = null;
		Messagepackage doc=new Messagepackage();
		doc.setDocText(text);
		doc.setDocByte(buffer);
		doc.setBesendedUser(beSendUser);
		doc.setSendUser(sendUser);
		doc.setPri(isPri);
		doc.setTime(formattedDateTime);
		doc.setDoc(true);
		if(!client.isClosed()) {
			try {
				if(isPri)
					if(text.endsWith(".png")||text.endsWith(".jpg")||text.endsWith(".bmp")||text.endsWith(".jrpg")||text.endsWith(".pgm")) {
						drawPicture(doc, buffer);
					}
				os = new ObjectOutputStream(client.getOutputStream());
				os.writeObject(doc);
				os.flush();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}

	}
	public void outputVideo(int port,String sendUser,String beSendUser){
		ObjectOutputStream os = null;
		Messagepackage doc=new Messagepackage();
		doc.setVideoPort(port);
		doc.setVideo(true);
		doc.setBesendedUser(beSendUser);
		doc.setSendUser(sendUser);
		doc.setPri(isPri);
		if(!client.isClosed()) {
			try {
				os = new ObjectOutputStream(client.getOutputStream());
				os.writeObject(doc);
				os.flush();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
	public void output(String s,String sendUser,String beSendUser,boolean isShake) throws UnknownHostException, IOException {
		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String formattedDateTime = currentDateTime.format(formatter);
		ObjectOutputStream os = null;
		Messagepackage msg=new Messagepackage();
		msg.setDocText("NULL");
		if(s!=null) {
			msg.setDocByte(s.getBytes());
		}
		msg.setBesendedUser(beSendUser);
		msg.setSendUser(sendUser);
		msg.setPri(isPri);
		msg.setTime(formattedDateTime);
		msg.setShake(isShake);

		if(!client.isClosed()) {
			try {
				if(isPri&&!isFirst) {

					if(isShake)
						privateUI.docs.insertString(privateUI.docs.getLength(), "                                                       "+msg.getSendUser()+"发来一个窗口抖动"+"\n", clientUI.style);
					else {
						privateUI.docs.insertString(privateUI.docs.getLength(), msg.getSendUser()+"  ", privateUI.style3);
						privateUI.docs.insertString(privateUI.docs.getLength(), msg.getTime()+"\n", privateUI.style);
						privateUI.docs.insertString(privateUI.docs.getLength(),"  "+new String(msg.getDocByte()) + "\n", privateUI.style2);
					}
				}
				os = new ObjectOutputStream(client.getOutputStream());
				os.writeObject(msg);
				os.flush();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (BadLocationException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}

	}
	public void inputDoc(Messagepackage doc) {
		new Thread(() -> {
			if(!doc.getSendUser().equals(user)){
				new Thread(()->{
					new MusicPlayer("src\\main\\java\\music.beep\\msgBeep.wav");
				}).start();
			}
			File file1 = new File(System.getProperty("user.home")+ "/Desktop", "聊天室downloads");
			if (!file1.exists()) {
				file1.mkdirs();
			}
			File file2 = new File(System.getProperty("user.home")+ "/Desktop"+"/聊天室downloads", "Document");
			file2.mkdirs();
			byte[] docbyte=doc.getDocByte();
			String text=doc.getDocText();
			String fileName = (System.getProperty("user.home") + "\\Desktop"+"\\聊天室downloads"+"\\Document\\" + text);
			if(text.endsWith(".png")||text.endsWith(".jpg")||text.endsWith(".bmp")||text.endsWith(".jrpg")||text.endsWith(".pgm")) {
				drawPicture(doc, docbyte);
			}else {
				try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
					if(!doc.getSendUser().equals(user)) {
						new FileReceiveUI(doc.getSendUser(), fileOutputStream, docbyte,clientUI);
						try {
							if(isPri) {
								privateUI.docs.insertString(privateUI.docs.getLength(), doc.getTime()+"\n", privateUI.style3);
								privateUI.docs.insertString(privateUI.docs.getLength(),"                                                   "+doc.getSendUser()+"发来一个文件" + "\n", privateUI.style2);
							}else {
								clientUI.docs.insertString(clientUI.docs.getLength(), doc.getTime()+"\n", clientUI.style);
								clientUI.docs.insertString(clientUI.docs.getLength(),"                                                   "+doc.getSendUser()+"发来一个文件" + "\n", clientUI.style2);

							}
						}catch (BadLocationException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}

					}


				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}

		}).start();
	}
	public void drawPicture(Messagepackage doc, byte[] docbyte) {
		try {
			if(isPri) {
				privateUI.docs.insertString(privateUI.docs.getLength(), doc.getTime()+"\n", privateUI.style3);
				privateUI.docs.insertString(privateUI.docs.getLength(),"  "+doc.getSendUser() + ":\n",privateUI.style3);
			}else {
				clientUI.docs.insertString(clientUI.docs.getLength(), doc.getTime()+"\n", clientUI.style);
				clientUI.docs.insertString(clientUI.docs.getLength(),"  "+doc.getSendUser() + ":\n", clientUI.style);
			}
		} catch (BadLocationException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		if(isPri)
			privateUI.msg.setCaretPosition(privateUI.docs.getLength());
		else
			clientUI.msg.setCaretPosition(clientUI.docs.getLength());
		ImageIcon icon = new ImageIcon(docbyte);
		int imageHeight = icon.getIconHeight();
		int lineHeight;
		if(isPri) {
			lineHeight = privateUI.msg.getFontMetrics(privateUI.msg.getFont()).getHeight();
		}
		else {
			lineHeight = clientUI.msg.getFontMetrics(clientUI.msg.getFont()).getHeight();
		}
		int lines = imageHeight / lineHeight;
		if(isPri)
			privateUI.msg.insertIcon(icon);
		else
			clientUI.msg.insertIcon(icon);
		for (int i = 0; i < lines; i++) {
			try {
				if(isPri)
					privateUI.docs.insertString(privateUI.docs.getLength(), "\n", null);
				else
					clientUI.docs.insertString(clientUI.docs.getLength(), "\n", null);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}


	public void stop() {
		try {
			ObjectOutputStream os = null;
			Messagepackage msg=new Messagepackage();
			msg.setServer(true);
			msg.setProblem("CLOSE");
			msg.setSendUser(user);
			if(!client.isClosed()) {
				try {
					os = new ObjectOutputStream(client.getOutputStream());
					os.writeObject(msg);
					os.flush();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}

		}finally {
			try {
				// 关闭输入流、输出流和socket
				if (os != null) {
					os.close();
				}
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
