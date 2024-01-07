package assignment.Project.function;

import assignment.Project.client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
public class ScreenShotUI extends JFrame implements ActionListener{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public JLabel text;
	JButton certain,quit;
	Client client;
	String path;
	JPanel jp1,jp2;
	String sendUser;
	String beSendUser;
	public ScreenShotUI(String path,Client client,String sendUser,String beSendUser) {
		// TODO 自动生成的构造函数存根
		text=new JLabel("是否发送该图片");
		text.setFont(new Font("黑体",Font.PLAIN,15));
		certain=new JButton("确定");
		quit=new JButton("取消");

//		quit.setPreferredSize(new Dimension(50,25));

		this.path=path;
		this.client=client;

		quit.addActionListener(this);
		certain.addActionListener(this);


		jp1=new JPanel();
		jp2=new JPanel();
		jp1.add(certain);
		jp1.add(quit);
		jp2.add(text);

		this.beSendUser=beSendUser;
		this.sendUser=sendUser;
		this.setLayout(new GridLayout(2,1));
		this.add(jp2);
		this.add(jp1);
//		this.setUndecorated(true);
		this.setSize(260,120);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource()==quit) {
			this.dispose();
		}else if(e.getSource()==certain) {
			try {
				client.outputDoc(path,sendUser,beSendUser);
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			this.dispose();
		}
	}
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
//		new ScreenShotUI(null,null);
	}

}
