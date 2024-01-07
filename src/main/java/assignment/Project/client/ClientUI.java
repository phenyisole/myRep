package assignment.Project.client;

import assignment.Project.database.RecordReading;
import assignment.Project.function.BackgroundPanel;
import assignment.Project.function.ScreenCapture;
import assignment.Project.tray.TrayManage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



public class ClientUI extends JFrame implements ActionListener{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    int changePort;
    boolean isLogin=false;
    Client client;
    String user;
    public RecordReading rr;
    boolean isFirstLogin;
    public TrayManage tray;
    public JPanel jp0,jp1,jp2,jp3,jp4,jp5,jp6,jp7,jp8,jp9,jp10,b1,b2,b3;
    public JList<String> users;
    public DefaultListModel<String> model ;
    public JScrollPane scrollPane,scrollPane2;
    public JTextArea sendmsg;
    public JTextPane msg;
    public StyledDocument docs;
    public Style style,style2,style3;
    public JTextField ip,port,userName;
    public JLabel text,onlineUsers,_ip,_port,_name,_send,content;
    public JButton send,login,quit,screenshot,senddoc,shakeWin,clear;//,set;
    public static void main(String[] args) {
        // TODO 自动生成的方法存根
        new ClientUI();
    }
    Map<String,PrivateUI> plist=new ConcurrentHashMap<>();
    public ClientUI(String s) {}

    public ClientUI(String server,String port,String name) {
        this();
        this.ip.setText(server);
        this.port.setText(port);
        this.userName.setText(name);
        if(!server.equals("")&&!port.equals("")&&!name.equals(""))
            login.doClick();
        isFirstLogin=false;
    }
    public ClientUI() {
        super();
        // TODO 自动生成的构造函数存根
        jp0 = new JPanel();
        jp1 = new JPanel(new BorderLayout());
        jp2 = new JPanel();
        jp3 = new JPanel(new BorderLayout());
        jp4 = new JPanel(new BorderLayout());
        jp5 = new JPanel();
        jp6 = new JPanel();
        jp7 = new JPanel();
        jp8 = new JPanel();
        jp9 = new JPanel();
        jp10=new JPanel(new BorderLayout());
        b1 = new JPanel();
        b2 = new JPanel();
        b3 = new JPanel();


        ip = new JTextField(10);
        port = new JTextField(10);
        userName = new JTextField(10);
        ip.setText("localhost");
        port.setText("1234");
        userName.setText("User-001");

        sendmsg = new JTextArea();
        sendmsg.setPreferredSize(new Dimension(500, 125));
        sendmsg.setLineWrap(true);
        sendmsg.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    send.doClick();

                }
            }
        });


        msg = new JTextPane();
        msg.setPreferredSize(new Dimension(585, 1000));
        msg.setEditable(false);
        docs = msg.getStyledDocument();
//        msg.setLineWrap(true);
        msg.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                File file1 = new File(System.getProperty("user.home")+ "/Desktop", "聊天室downloads");
                if (!file1.exists()) {
                    file1.mkdirs(); // 创建文件夹
                }
                File file2 = new File(System.getProperty("user.home")+ "/Desktop"+"/聊天室downloads", "Picture");
                file2.mkdirs();
                String fileName = (System.getProperty("user.home") + "\\Desktop"+"\\聊天室downloads"+"\\picture\\");
                if (SwingUtilities.isRightMouseButton(e)) {
                    int pos = msg.viewToModel2D(e.getPoint());
                    Element elem = docs.getCharacterElement(pos);
                    AttributeSet as = elem.getAttributes();
                    Icon icon = StyleConstants.getIcon(as);
                    if (icon != null) {
                        JPopupMenu menu = new JPopupMenu();
                        JMenuItem saveItem = new JMenuItem("保存图片");
                        saveItem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                BufferedImage image=new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
                                Graphics2D g2d = image.createGraphics();
                                icon.paintIcon(null, g2d, 0, 0);
                                g2d.dispose();
                                try {
                                    String ss = String.valueOf(System.currentTimeMillis());
                                    ImageIO.write(image, "png", new File(fileName+ss+".jpg"));
                                } catch (IOException e1) {
                                    // TODO 自动生成的 catch 块
                                    e1.printStackTrace();
                                }
                            }
                        });
                        menu.add(saveItem);
                        menu.show(msg, e.getX(), e.getY());
                    }else {

                        JPopupMenu popupMenu = new JPopupMenu();

                        // 添加菜单项
                        JMenuItem menuItem = new JMenuItem("黑体");
                        JMenuItem menuItem2 = new JMenuItem("宋体");
                        JMenuItem menuItem3 = new JMenuItem("楷体");
                        JLabel label = new JLabel(" 字体选择：");
                        label.setHorizontalAlignment(SwingConstants.CENTER);
                        popupMenu.add(label);
                        popupMenu.add(menuItem);
                        popupMenu.add(menuItem2);
                        popupMenu.add(menuItem3);
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                        menuItem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                StyleConstants.setFontFamily(style3, "黑体");
                                StyleConstants.setFontFamily(style2, "黑体");
                                StyleConstants.setFontFamily(style, "黑体");
                                try {
                                    docs.remove(0, docs.getLength());
                                    rr.read();
                                } catch (BadLocationException e1) {
                                    e1.printStackTrace();
                                }

                            }
                        });
                        menuItem2.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                StyleConstants.setFontFamily(style3, "宋体");
                                StyleConstants.setFontFamily(style2, "宋体");
                                StyleConstants.setFontFamily(style, "宋体");
                                try {
                                    docs.remove(0, docs.getLength());
                                    rr.read();
                                } catch (BadLocationException e1) {
                                    e1.printStackTrace();
                                }

                            }
                        });
                        menuItem3.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {

                                StyleConstants.setFontFamily(style3, "楷体");
                                StyleConstants.setFontFamily(style2, "楷体");
                                StyleConstants.setFontFamily(style, "楷体");
                                try {
                                    docs.remove(0, docs.getLength());
                                    rr.read();
                                } catch (BadLocationException e1) {
                                    e1.printStackTrace();
                                }

                            }
                        });
                    }
                }
            }
        });

        model = new DefaultListModel<>();
        users = new JList<>(model);
        users.setPreferredSize(new Dimension(100, 400));
        users.addMouseListener(new MouseAdapter(){
            private long lastClickTime = 0;
            private final long doubleClickDelay = 1000;

            @Override
            public void mouseClicked(MouseEvent e) {
                long clickTime = System.currentTimeMillis();

                // 判断两次点击时间间隔是否小于双击延迟时间
                if (clickTime - lastClickTime < doubleClickDelay) {
                    String selectedValue = (String) users.getSelectedValue();
                    if(selectedValue.equals(userName.getText())) {
                        JOptionPane.showMessageDialog(null, "不能私聊自己哦~","提示消息",JOptionPane.WARNING_MESSAGE);
                    }else {
                        if(plist.containsKey(selectedValue))
                            plist.get(selectedValue).setVisible(true);
                        else
                            plist.put(selectedValue, new PrivateUI(port.getText(),ip.getText(),(String)users.getSelectedValue(),userName.getText()));

                    }

                }

                lastClickTime = clickTime;
            }
        });


        scrollPane2 = new JScrollPane(msg);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane2.setPreferredSize(new Dimension(585, 185));
        jp10.add(scrollPane2);

        text = new JLabel("                                                                                                                                                                        ★★★★★");
        onlineUsers = new JLabel(" 在线用户列表");
        _ip = new JLabel("服务器");
        _port = new JLabel("端口号");
        _name = new JLabel("用户");
        _send = new JLabel(" 发送消息");
        content = new JLabel("  聊天内容");

        send = new JButton("发送");
        login = new JButton("连接");
        quit = new JButton("离开");
        screenshot=new JButton("截图");
        senddoc=new JButton("文件");
        shakeWin=new JButton("抖动");
        clear=new JButton("清空");
//        set=new JButton("设置");

        screenshot.setPreferredSize(new Dimension(30, 25));
        senddoc.setPreferredSize(new Dimension(30, 25));
        shakeWin.setPreferredSize(new Dimension(30, 25));
        clear.setPreferredSize(new Dimension(30, 25));
//        set.setPreferredSize(new Dimension(27, 27));

        ImageIcon icon = new ImageIcon("src\\main\\java\\组件\\screenshot2.png");
        Image image = icon.getImage();
        screenshot.setIcon(getIcon(image));

        ImageIcon icon2 = new ImageIcon("src\\main\\java\\组件\\doc.png");
        Image image2 = icon2.getImage();
        senddoc.setIcon(getIcon(image2));

        ImageIcon icon3 = new ImageIcon("src\\main\\java\\组件\\shakeWin.png");
        Image image3 = icon3.getImage();
        shakeWin.setIcon(getIcon(image3));

        ImageIcon icon4 = new ImageIcon("src\\main\\java\\组件\\clear.png");
        Image image4 = icon4.getImage();
        clear.setIcon(getIcon(image4));


        send.addActionListener(this);
        login.addActionListener(this);
        quit.addActionListener(this);
        senddoc.addActionListener(this);
        screenshot.addActionListener(this);
        clear.addActionListener(this);
//        set.addActionListener(this);
        shakeWin.addActionListener(this);
        getRootPane().setDefaultButton(send);

//        jp0.add(set);
        jp0.add(text);

        jp1.add(onlineUsers,BorderLayout.NORTH);
        jp1.add(jp8,BorderLayout.SOUTH);

        jp2.add(_ip);
        jp2.add(ip);
        jp2.add(Box.createHorizontalStrut(10));
        jp2.add(_port);
        jp2.add(port);
        jp2.add(Box.createHorizontalStrut(10));
        jp2.add(_name);
        jp2.add(userName);
        jp2.add(Box.createHorizontalStrut(10));
        jp2.add(login);
        jp2.add(Box.createHorizontalStrut(10));
        jp2.add(clear);

        jp3.add(content,BorderLayout.NORTH);
        jp3.add(jp10,BorderLayout.SOUTH);

        jp4.add(_send,BorderLayout.NORTH);
        jp4.add(sendmsg,BorderLayout.SOUTH);

        b3.add(screenshot);
        b3.add(senddoc);
        b3.add(shakeWin);
        b1.add(send);
        b2.add(quit);


        jp5.setLayout(new BoxLayout(jp5, BoxLayout.Y_AXIS));
        b3.setBorder(BorderFactory.createEmptyBorder(25, 0, -30, 0));
        b1.setBorder(BorderFactory.createEmptyBorder(0, 0, -30, 0));
        b2.setBorder(BorderFactory.createEmptyBorder(0, 0, -20, 0));

        jp5.add(b3);
        jp5.add(b1);
        jp5.add(b2);

        jp7.setLayout(new BoxLayout(jp7, BoxLayout.X_AXIS));
        jp5.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        jp7.add(jp4);
        jp7.add(jp5);

        jp8.add(users);

        jp6.setLayout(new BoxLayout(jp6, BoxLayout.Y_AXIS));
        jp0.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        jp2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jp3.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        jp7.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        jp6.add(jp0);
        jp6.add(jp2);
        jp6.add(jp3);
        jp6.add(jp7);

        Dimension buttonSize = new Dimension(90, 35);
        send.setPreferredSize(buttonSize);
        quit.setPreferredSize(buttonSize);


//		jp10.add(msg);

        jp9.setLayout(new BoxLayout(jp9, BoxLayout.X_AXIS));
        jp1.setBorder(BorderFactory.createEmptyBorder(15, 13, 10, 0));
        jp6.setBorder(BorderFactory.createEmptyBorder(0, 2, 10, 10));
        jp9.add(jp1);
        jp9.add(jp6);

        jp0.setOpaque(false);
        jp1.setOpaque(false);
        jp2.setOpaque(false);
        jp3.setOpaque(false);
        jp4.setOpaque(false);
        jp5.setOpaque(false);
        jp6.setOpaque(false);
        jp7.setOpaque(false);
        jp8.setOpaque(false);
        jp9.setOpaque(false);
        jp10.setOpaque(false);
        b1.setOpaque(false);
        b2.setOpaque(false);
        b3.setOpaque(false);

        this.setContentPane(new BackgroundPanel("src\\main\\java\\背景\\bz4.jpg"));
        this.add(jp9);

        this.setTitle("聊天室客户端 v2 . 1 . 0");
        this.setSize(750,500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                client=null;
                System.exit(0);
            }
        });
        this.setVisible(true);
        this.setResizable(false);

        style = msg.addStyle("MyStyle", null);
        style2 = msg.addStyle("MyStyle2", null);
        style3 = msg.addStyle("MyStyle3", null);
        StyleConstants.setForeground(style, Color.gray);
        StyleConstants.setForeground(style2, Color.blue);
        StyleConstants.setForeground(style3, Color.red);

        tray=new TrayManage(this);
        tray.addTray();
    }

    public ImageIcon getIcon(Image image) {
        return new ImageIcon(image) {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                // 获取按钮的尺寸
                int width = c.getWidth();
                int height = c.getHeight();

                // 绘制图片，使其铺满按钮
                g.drawImage(image, 0, 0, width, height, null);
            }
        };
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO 自动生成的方法存根
        if(e.getSource()==login) {
            if(ip.getText().isEmpty()||port.getText().isEmpty()||userName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "请填写完整信息","提示消息",JOptionPane.WARNING_MESSAGE);
            }else {
                if(isConvertibleToInt()) {//&&isValidIPAddress()
                    client=new Client(ip.getText(),changePort,this,userName.getText(),false);
                    try {
                        client.startShutdownHook();
                    } catch (IOException e1) {
                        // TODO 自动生成的 catch 块
                        e1.printStackTrace();
                    }
                    if(client.monitor) {
                        login.setEnabled(false);
                        isLogin=true;
                        userName.setEditable(false);
                        ip.setEditable(false);
                        port.setEditable(false);
                        if(isFirstLogin) {
                            JOptionPane.showMessageDialog(null, "登录成功！","提示消息",JOptionPane.WARNING_MESSAGE);

                        }else
                            JOptionPane.showMessageDialog(null, "连接成功!","提示消息",JOptionPane.WARNING_MESSAGE);
                        try {
                            rr=new RecordReading(this);
                        } catch (BadLocationException e1) {
                            // TODO 自动生成的 catch 块
                            e1.printStackTrace();
                        }
                        try {
                            client.inputDivide();
                        } catch (Exception e1) {
                            // TODO 自动生成的 catch 块
                            e1.printStackTrace();
                        }
                        user=userName.getText();
                    }else {
                        client=null;
                    }
                }
            }
        }else if(e.getSource()==clear) {
            msg.setText("");
        }
        else if(e.getSource()==send) {
            if(!isLogin) {
                JOptionPane.showMessageDialog(null, "请先连接","提示消息",JOptionPane.WARNING_MESSAGE);
            }else if(sendmsg.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "不能发送空白哦~","提示消息",JOptionPane.WARNING_MESSAGE);

            }else {
                try {
                    client.output(sendmsg.getText(),userName.getText(),"ALL",false);
                    sendmsg.setText("");
                } catch (IOException e1) {
                    // TODO 自动生成的 catch 块
                    JOptionPane.showMessageDialog(null, "发送失败","提示消息",JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        else if(e.getSource()==quit) {
            client.stop();
            client=null;
            isLogin=false;
            ip.setEditable(true);
            port.setEditable(true);
            login.setEnabled(true);
//        	userName.setEditable(true);
            for(PrivateUI pu:plist.values())
                pu.dispose();
            JOptionPane.showMessageDialog(null, "已退出!","提示消息",JOptionPane.WARNING_MESSAGE);
            model.clear();
        }
        else if(e.getSource()==screenshot) {
            if(!isLogin) {
                JOptionPane.showMessageDialog(null, "请先连接","提示消息",JOptionPane.WARNING_MESSAGE);
            }else
                new ScreenCapture(client,userName.getText(),"ALL");
        }
        else if(e.getSource()==senddoc) {
            if(!isLogin) {
                JOptionPane.showMessageDialog(null, "请先连接","提示消息",JOptionPane.WARNING_MESSAGE);
            }else {
                Point location = this.getLocation();
                int x = location.x;
                int y = location.y;
                new FileOutputUI(x+680,y+260,client,userName.getText(),"ALL");
            }
        }else if(e.getSource()==shakeWin) {
            if(!isLogin) {
                JOptionPane.showMessageDialog(null, "请先连接","提示消息",JOptionPane.WARNING_MESSAGE);
            }else {
                try {
                    client.output(null,userName.getText(),"ALL",true);
                } catch (UnknownHostException e1) {
                    // TODO 自动生成的 catch 块
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO 自动生成的 catch 块
                    e1.printStackTrace();
                }
            }
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
}
