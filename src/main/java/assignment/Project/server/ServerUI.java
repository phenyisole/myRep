package assignment.Project.server;


import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;
import assignment.Project.function.BackgroundPanel;
import java.net.UnknownHostException;

import javax.swing.*;

public class ServerUI extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int changePort;
	Server server;
	public JTextField port,msg;
	public JPanel jp0,jp1,jp2,jp3,jp4,jp5,jp6,jp7;
	public JList<String> users;
	DefaultListModel<String> model = new DefaultListModel<>();
	public JScrollPane scrollPane;
	public JLabel onlineUsers,_port,onlineNum,text,_msg;
	public JButton open,close;
	public ServerUI() {
		port = new JTextField(10);
		port.setText("1234");
		msg=new JTextField();
		msg.setPreferredSize(new Dimension(450, 30));
		msg.setEditable(false);
		
		users = new JList<>(model);
		users.setPreferredSize(new Dimension(450, 300));
		
		scrollPane = new JScrollPane(users);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(450, 300));
		
		jp0 = new JPanel();
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel(new BorderLayout());
		jp4 = new JPanel(new BorderLayout());
		jp5 = new JPanel();
		jp6 = new JPanel(new BorderLayout());
		jp7=new JPanel();
		
		onlineUsers = new JLabel("  在线用户列表");
		_port = new JLabel(" 服务器端口号");
		onlineNum = new JLabel("  在线人数：");
		text=new JLabel("客户端连接监听                                                                                                       ★★★★★");
		_msg=new JLabel("  消息"); 
		
		open = new JButton("开启服务器");
		close = new JButton("停止服务器");
		open.addActionListener(this);
		close.addActionListener(this);
		open.setPreferredSize(new Dimension(open.getPreferredSize().width, 25));
		close.setPreferredSize(new Dimension(close.getPreferredSize().width, 25));
		
		jp1.add(text);
		
		jp2.add(_port);
		jp2.add(port);
		jp2.add(Box.createHorizontalStrut(40));
		jp2.add(open);
		jp2.add(Box.createHorizontalStrut(40));
		jp2.add(close);
		
		jp0.add(users);
		jp3.add(onlineUsers,BorderLayout.NORTH);
		jp3.add(jp0,BorderLayout.SOUTH);
		
		jp7.add(msg);
		jp6.add(_msg,BorderLayout.NORTH);
		jp6.add(jp7,BorderLayout.SOUTH);
		
		jp4.add(onlineNum,BorderLayout.WEST);
		
		jp5.setLayout(new BoxLayout(jp5, BoxLayout.Y_AXIS));
		jp1.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		jp2.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		jp3.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		jp6.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		jp4.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		
		jp5.add(jp1);
		jp5.add(jp2);
		jp5.add(jp3);
		jp5.add(jp6);
		jp5.add(jp4);
		jp0.setOpaque(false);
		jp1.setOpaque(false);
		jp2.setOpaque(false);
		jp3.setOpaque(false);
		jp4.setOpaque(false);
		jp5.setOpaque(false);
		jp6.setOpaque(false);
		jp7.setOpaque(false);
		
		// ... and so on for all your JPanels
		this.setContentPane(new BackgroundPanel("src\\main\\java\\背景\\bz4.jpg"));
		this.add(jp5);
		this.setTitle("聊天室服务器");
		this.setSize(500,600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
	}
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		new ServerUI();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource()==open) {
			if(port.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "请输入端口号!","提示消息",JOptionPane.WARNING_MESSAGE);
			}else if(isConvertibleToInt()){
				try {
					server=new Server(changePort,this);
					server.startServer();
					open.setEnabled(false);
					model.clear();
			    	int itemCount = users.getModel().getSize();
					onlineNum.setText("在线人数："+itemCount);
					msg.setText("服务器已经启动,正在监听"+getIp()+" 端口号："+port.getText()+".................");
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					
				};
			}
		}
		if(e.getSource()==close) {
	    	model.clear();
	    	int itemCount = users.getModel().getSize();
			onlineNum.setText("在线人数："+itemCount);
			server.stopServer();
			open.setEnabled(true);
			msg.setText("服务器已关闭..........");
		}
	}
	public boolean isConvertibleToInt() {
		try {
			changePort = Integer.parseInt(port.getText());
			return true;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "端口号格式错误!","提示消息",JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
	public String getIp() {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			return inetAddress.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
}
