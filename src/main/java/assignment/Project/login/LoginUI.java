package assignment.Project.login;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import assignment.Project.function.BackgroundPanel;
import assignment.Project.client.ClientUI;


public class LoginUI extends JFrame implements ActionListener{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public JPanel jp00,jp0,jp1,jp2,jp3,jp4,jp5,jp6;
	public JPanel jp20,jp21,jp22,jp23,jp24;
	public JPanel jp30,jp31,jp32,jp33,jp34,jp35,jp36,jp37,jp38;
	public JPanel jp40,jp41,jp42,jp43,jp44;
	public CardLayout cardLayout;
	public JMenuBar menuBar;
	public JMenu menu;
	public JMenuItem loginPart,setPart ;
	public String getForgetPass;
	public String answers;

	public JLabel header,user,password,reset,quit,resgister,forget,empty,space;
	public JTextField accountInput,registerInput;
	public JButton login;

	public JLabel user2,password2,password22,header3,passQues,passAns,goback,space2;
	public JTextField user2Input,question,answer;
	public JButton cancel,register,next;

	public JLabel server,port,header2;
	public JTextField serverInput,portInput;

	public JLabel header4,forgetAccount,forgetQues,forgetPass,space3;
	public JTextField forgetA,forgetP;
	public JButton search,getPass;

	public JPasswordField passwordInput,registerPass,repeatPass;
	public LoginUI(){
		jp1 =new JPanel();//容器
		jp2 =new JPanel();
		jp3 =new JPanel();
		jp4 =new JPanel();
		jp5 =new JPanel();
		jp6 =new JPanel(new BorderLayout());
		jp0 =new JPanel();
		jp00 =new JPanel(new CardLayout());

		jp20 =new JPanel(new GridLayout(3,1));
		jp21 =new JPanel();
		jp22 =new JPanel();
		jp23 =new JPanel();
		jp24 =new JPanel();

		jp40=new JPanel(new GridLayout(5,1));
		jp41=new JPanel();
		jp42=new JPanel();
		jp43=new JPanel();
		jp44=new JPanel();

		jp30=new JPanel(new GridLayout(5,1));
		jp31=new JPanel();
		jp32=new JPanel();
		jp33=new JPanel();
		jp34=new JPanel();
		jp35=new JPanel(new GridLayout(3,1));
		jp36=new JPanel();
		jp37=new JPanel();
		jp38=new JPanel();


		server = new JLabel("服务器:");
		header4 = new JLabel("暂停服务，请联系管理员");
		port= new JLabel("端口号:");
		header2 = new JLabel("配置连接",JLabel.CENTER);
		space=new JLabel("                     ");

		serverInput = new JTextField(12);
		portInput = new JTextField(5);
		serverInput.setText("localhost");
		portInput.setText("1234");

		menuBar = new JMenuBar(); // 初始化菜单栏
		menu = new JMenu("高级");
		loginPart = new JMenuItem("登录"); // 创建菜单项并指定名称
		setPart = new JMenuItem("配置"); // 创建菜单项并指定名称
		loginPart.addActionListener(this); // 为菜单项添加事件监听器
		setPart.addActionListener(this); // 为菜单项添加事件监听器
		menu.add(loginPart); // 将菜单项添加到菜单中
		menu.add(setPart); // 将菜单项添加到菜单中
		menuBar.add(menu);

		header =new JLabel("Java聊天室",JLabel.CENTER);//标签
		user =new JLabel("用户名");
		password =new JLabel("密    码");
		empty =new JLabel("                   ");
		space2 =new JLabel("             ");

		user2 = new JLabel("账号");
		passQues=new JLabel("密保问题");
		passAns=new JLabel("答案");
		password2 = new JLabel("密码");
		password22 = new JLabel("重复密码");

		header3 = new JLabel("注册页面",JLabel.CENTER);
		header3.setFont(new Font("楷体",Font.PLAIN,20));
		header.setFont(new Font("华文行楷",Font.PLAIN,33));
		header2.setFont(new Font("楷体",Font.PLAIN,20));
		header4 = new JLabel("找回密码",JLabel.CENTER);
		header4.setFont(new Font("楷体",Font.PLAIN,20));
		header.setPreferredSize(new Dimension(380,50));
		header2.setPreferredSize(new Dimension(200,40));
		header3.setPreferredSize(new Dimension(200,40));
		header4.setPreferredSize(new Dimension(200,40));

		forget=new JLabel("<html><u>忘记密码？</u></html>");
		forgetAccount = new JLabel("请输入账号:");
		forgetQues = new JLabel("",JLabel.CENTER);
		space3=new JLabel("                  ");
		forgetPass = new JLabel("请输入答案:");
		forgetA = new JTextField(10);
		forgetP = new JTextField(10);
		search = new JButton("查找");
		getPass = new JButton("找回密码");
		forgetPass.setVisible(false);
		getPass.setVisible(false);
		forgetP.setVisible(false);

		login =new JButton();
		cancel=new JButton("取消");
		register=new JButton("注册");
		next=new JButton("下一步");

		goback=new JLabel("上一步");
		reset =new JLabel("重置");
		quit =new JLabel("退出");
		resgister=new JLabel("注册");
		login.setPreferredSize(new Dimension(100,30));
		cancel.setPreferredSize(new Dimension(60,25));
		register.setPreferredSize(new Dimension(60,25));

		login.setText("登录");

		login.addActionListener(this);//监听
		register.addActionListener(this);//监听
		cancel.addActionListener(this);//监听
		next.addActionListener(this);//监听
		search.addActionListener(this);
		getPass.addActionListener(this);

		goback.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 这里处理点击事件
				cardLayout.show(jp00, "register");
			}
			public void mouseEntered(MouseEvent e) {
				// 鼠标进入标签时，改变标签的前景色
				goback.setForeground(Color.blue);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// 鼠标离开标签时，恢复标签的前景色
				goback.setForeground(Color.BLACK);
			}
		});
		reset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 这里处理点击事件
				clear();
			}
			public void mouseEntered(MouseEvent e) {
				// 鼠标进入标签时，改变标签的前景色
				reset.setForeground(Color.blue);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// 鼠标离开标签时，恢复标签的前景色
				reset.setForeground(Color.BLACK);
			}
		});
		quit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 这里处理点击事件
				System.exit(0);
			}
			public void mouseEntered(MouseEvent e) {
				// 鼠标进入标签时，改变标签的前景色
				quit.setForeground(Color.blue);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// 鼠标离开标签时，恢复标签的前景色
				quit.setForeground(Color.BLACK);
			}
		});

		resgister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 这里处理点击事件
				cardLayout.show(jp00, "register");
			}
			public void mouseEntered(MouseEvent e) {
				// 鼠标进入标签时，改变标签的前景色
				resgister.setForeground(Color.blue);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// 鼠标离开标签时，恢复标签的前景色
				resgister.setForeground(Color.BLACK);
			}
		});
		forget.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 这里处理点击事件
				cardLayout.show(jp00, "reset");
			}
			public void mouseEntered(MouseEvent e) {
				// 鼠标进入标签时，改变标签的前景色
				forget.setForeground(Color.blue);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// 鼠标离开标签时，恢复标签的前景色
				forget.setForeground(Color.BLACK);
			}
		});
		getRootPane().setDefaultButton(login);//设置默认按钮

		accountInput =new JTextField(10);//输入
		user2Input=new JTextField(10);
		question=new JTextField(8);
		answer=new JTextField(10);
		passwordInput =new JPasswordField(10);
		registerPass=new JPasswordField(10);
		repeatPass=new JPasswordField(8);

//		this.setLayout(new BorderLayout());
//		this.add(header,BorderLayout.NORTH);
		jp5.setLayout(new BoxLayout(jp5, BoxLayout.Y_AXIS));
		this.setLayout(new BorderLayout());
//		header.setPreferredSize(new Dimension(900,20));

		jp1.add(user);
		jp1.add(accountInput);

		jp2.add(password);
		jp2.add(passwordInput);

		jp0.add(empty);
		jp0.add(login);
		jp0.add(forget);

		jp4.add(reset);
		jp4.add(resgister);
		jp4.add(quit);


		//		jp1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
//		jp2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
//		jp4.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
//		jp0.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		jp5.add(jp1);
		jp5.add(jp2);
		jp5.add(jp4);
		jp5.add(jp0);

		jp6.add(jp5,BorderLayout.CENTER);
		jp6.add(header,BorderLayout.NORTH);

		jp0.setOpaque(false);
		jp1.setOpaque(false);
		jp2.setOpaque(false);
		jp3.setOpaque(false);
		jp4.setOpaque(false);
		jp5.setOpaque(false);
		jp6.setOpaque(false);
		menuBar.setOpaque(false);
		jp00.setOpaque(false);
		menu.setOpaque(false);



		jp21.add(server);
		jp21.add(serverInput);
		jp22.add(port);
		jp22.add(portInput);
		jp22.add(space);

		jp20.add(header2);
		jp20.add(jp21);
		jp20.add(jp22);


		jp20.setOpaque(false);
		jp21.setOpaque(false);
		jp22.setOpaque(false);
		jp23.setOpaque(false);
		jp24.setOpaque(false);

		jp40.add(header4);
		jp41.add(forgetAccount);
		jp41.add(forgetA);
		jp41.add(search);
		jp42.add(forgetQues);
		jp43.add(forgetPass);
		jp43.add(forgetP);
		jp43.add(space3);
		jp44.add(getPass);
		jp40.add(jp41);
		jp40.add(jp42);
		jp40.add(jp43);
		jp40.add(jp44);

		jp40.setOpaque(false);
		jp41.setOpaque(false);
		jp42.setOpaque(false);
		jp43.setOpaque(false);
		jp44.setOpaque(false);

		jp31.add(user2);
		jp31.add(user2Input);
		jp32.add(password2);
		jp32.add(registerPass);
		jp33.add(password22);
		jp33.add(repeatPass);
		jp34.add(next);
		jp30.add(header3);
		jp30.add(jp31);
		jp30.add(jp32);
		jp30.add(jp33);
		jp30.add(jp34);

		jp36.add(passQues);
		jp36.add(question);
		jp37.add(passAns);
		jp37.add(answer);
		jp38.add(space2);
		jp38.add(register);
		jp38.add(cancel);
		jp38.add(goback);
		jp35.add(jp36);
		jp35.add(jp37);
		jp35.add(jp38);

		jp30.setOpaque(false);
		jp31.setOpaque(false);
		jp32.setOpaque(false);
		jp33.setOpaque(false);
		jp34.setOpaque(false);
		jp35.setOpaque(false);
		jp36.setOpaque(false);
		jp37.setOpaque(false);
		jp38.setOpaque(false);

		jp00.add(jp6,"login");
		jp00.add(jp20,"set");
		jp00.add(jp40,"reset");
		jp00.add(jp30,"register");
		jp00.add(jp35,"register2");
		this.setContentPane(new BackgroundPanel("src\\main\\java\\背景\\bz4.jpg"));
		this.add(jp00);
		this.setJMenuBar(menuBar);

		cardLayout = (CardLayout) jp00.getLayout();
		this.setTitle("登录");
		this.setSize(330,260);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);

	}
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		new LoginUI();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource()==login) {
			if(!isEmpty(this.accountInput.getText(), String.valueOf(this.passwordInput.getPassword()))) {
				if(new Login(serverInput.getText(), Integer.parseInt(portInput.getText()), accountInput.getText(),new String(passwordInput.getPassword()),"NULL","NULL",false).check()) {
//					JOptionPane.showMessageDialog(null, "登录成功！","提示消息",JOptionPane.WARNING_MESSAGE);
					this.setVisible(false);
					new ClientUI(serverInput.getText(),portInput.getText(),accountInput.getText());
					this.dispose();
				}
				else{
					JOptionPane.showMessageDialog(this, "用户名或密码错误！","提示消息",JOptionPane.WARNING_MESSAGE);
					clear();
				}
			}
		}else if(e.getSource()==setPart) {
			cardLayout.show(jp00, "set");
		}else if(e.getSource()==loginPart) {
			cardLayout.show(jp00, "login");
		}else if(e.getSource()==cancel) {
			cardLayout.show(jp00, "login");
		}else if(e.getSource()==register) {
			if(user2Input.getText().isEmpty()||registerPass.getPassword().length==0||repeatPass.getPassword().length==0||question.getText().isEmpty()||answer.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "请输入完整注册信息!","提示消息",JOptionPane.WARNING_MESSAGE);
			}else {
				if(!Arrays.equals(registerPass.getPassword(),repeatPass.getPassword())) {
					JOptionPane.showMessageDialog(this, "两次输入的密码不相同","提示消息",JOptionPane.WARNING_MESSAGE);
				}else {
					if(new Login(serverInput.getText(), Integer.parseInt(portInput.getText()),user2Input.getText(),new String(repeatPass.getPassword()),question.getText(),answer.getText(),true).check()) {
						JOptionPane.showMessageDialog(null, "注册成功！","提示消息",JOptionPane.WARNING_MESSAGE);
						cardLayout.show(jp00, "login");
					}else {
						JOptionPane.showMessageDialog(this, "用户名已被注册","提示消息",JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		}else if(e.getSource()==next) {
			cardLayout.show(jp00, "register2");
		}else if(e.getSource()==search) {
			if(forgetA.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "请输入账号","提示消息",JOptionPane.WARNING_MESSAGE);
			}else
			if(new RetrievePass(this).check()) {
				forgetPass.setVisible(true);
				getPass.setVisible(true);
				forgetP.setVisible(true);
				forgetQues.repaint();
				forgetP.setText("");
			}else {
				JOptionPane.showMessageDialog(this, "用户名错误","提示消息",JOptionPane.WARNING_MESSAGE);
			}
		}else if(e.getSource()==getPass) {
			if(forgetP.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "请输入答案","提示消息",JOptionPane.WARNING_MESSAGE);
			}else
			if(forgetP.getText().equals(answers)) {
				JOptionPane.showMessageDialog(this, "密码为:"+getForgetPass,"提示消息",JOptionPane.WARNING_MESSAGE);
				getForgetPass=null;
				answers=null;
				forgetPass.setVisible(false);
				getPass.setVisible(false);
				forgetP.setVisible(false);
				forgetQues.setText("");
				forgetQues.repaint();
				forgetA.setText("");

			}else
				JOptionPane.showMessageDialog(this, "答案错误","提示消息",JOptionPane.WARNING_MESSAGE);
		}
	}
	public boolean isEmpty(String username,String pwd) {
		if (this.accountInput.getText().isEmpty()||this.passwordInput.getPassword().length==0) {
			JOptionPane.showMessageDialog(this, "请输入用户名和密码!","提示消息",JOptionPane.WARNING_MESSAGE);
			return true;
		}
		return false;
	}
	private void clear() {
		// TODO 自动生成的方法存根
		this.accountInput.setText("");
		this.passwordInput.setText("");
	}

}
