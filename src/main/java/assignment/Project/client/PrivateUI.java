package assignment.Project.client;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.text.Style;
import java.awt.image.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.text.StyleConstants;
import javax.swing.text.*;
import assignment.Project.function.BackgroundPanel;
import assignment.Project.function.ScreenCapture;
import assignment.Project.function.ShakeWin;
import assignment.Project.video.VideoServer;

import javax.imageio.ImageIO;
import javax.swing.*;


//import assignment.Project.database.GetAndPostUsers;



public class PrivateUI extends ClientUI implements ActionListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public JPanel jp0, jp1, jp3, jp4, jp5, jp6, jp7, jp8, jp10, b1, b2, b3;
    public JScrollPane scrollPane, scrollPane2;
    public JTextArea sendmsg;
    public JTextPane msg;
    public Style style, style2, style3;
    public StyledDocument docs;
    public JLabel text, onlineUsers, _send, content, space;
    public JButton send, login, quit, screenshot, senddoc, shakeWin, clear, video;
    int changePort;
    Client client;
    String user, username, ip;

    public PrivateUI(String port, String ip, String user, String userName) {
        super(null);
        jp0 = new JPanel();
        jp1 = new JPanel(new BorderLayout());
        jp3 = new JPanel(new BorderLayout());
        jp4 = new JPanel(new BorderLayout());
        jp5 = new JPanel();
        jp6 = new JPanel();
        jp7 = new JPanel();
        jp8 = new JPanel();
        jp10 = new JPanel(new BorderLayout());
        b1 = new JPanel();
        b2 = new JPanel();
        b3 = new JPanel();

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
        this.ip = ip;
        msg = new JTextPane();
        msg.setPreferredSize(new Dimension(585, 1000));
        msg.setEditable(false);
        docs = msg.getStyledDocument();
//        msg.setLineWrap(true);
        msg.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                File file1 = new File(System.getProperty("user.home") + "/Desktop", "聊天室downloads");
                if (!file1.exists()) {
                    file1.mkdirs(); // 创建文件夹
                }
                File file2 = new File(System.getProperty("user.home") + "/Desktop" + "/聊天室downloads", "Picture");
                file2.mkdirs();
                String fileName = (System.getProperty("user.home") + "\\Desktop" + "\\聊天室downloads" + "\\picture\\");
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
                                BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
                                Graphics2D g2d = image.createGraphics();
                                icon.paintIcon(null, g2d, 0, 0);
                                g2d.dispose();
                                try {
                                    String ss = String.valueOf(System.currentTimeMillis());
                                    ImageIO.write(image, "png", new File(fileName + ss + ".jpg"));
                                } catch (IOException e1) {
                                    // TODO 自动生成的 catch 块
                                    e1.printStackTrace();
                                }
                            }
                        });
                        menu.add(saveItem);
                        menu.show(msg, e.getX(), e.getY());
                    }
                }
            }
        });


        scrollPane2 = new JScrollPane(msg);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane2.setPreferredSize(new Dimension(585, 185));
        jp10.add(scrollPane2);

        space = new JLabel("                                                         ");
        _send = new JLabel(" 发送消息");
        content = new JLabel("  聊天内容");
        video = new JButton("视频");
        send = new JButton("发送");
        quit = new JButton("离开");
        screenshot = new JButton("截图");
        senddoc = new JButton("文件");
        shakeWin = new JButton("抖动");
        clear = new JButton("清空");

        video.setPreferredSize(new Dimension(30, 25));
        screenshot.setPreferredSize(new Dimension(30, 25));
        senddoc.setPreferredSize(new Dimension(30, 25));
        shakeWin.setPreferredSize(new Dimension(30, 25));
        clear.setPreferredSize(new Dimension(30, 25));

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

        ImageIcon icon5 = new ImageIcon("src\\main\\java\\组件\\video.png");
        Image image5 = icon5.getImage();
        video.setIcon(getIcon(image5));

        text = new JLabel("                             " + user);
        text.setFont(new Font("楷体", Font.BOLD, 15));
        jp0.add(text);
        jp0.add(clear);
        jp0.add(space);
        jp0.add(video);
        video.addActionListener(this);
        send.addActionListener(this);
        quit.addActionListener(this);
        clear.addActionListener(this);
        senddoc.addActionListener(this);
        screenshot.addActionListener(this);
        shakeWin.addActionListener(this);
        getRootPane().setDefaultButton(send);


        jp3.add(content, BorderLayout.NORTH);
        jp3.add(jp10, BorderLayout.SOUTH);

        jp4.add(_send, BorderLayout.NORTH);
        jp4.add(sendmsg, BorderLayout.SOUTH);

        b3.add(screenshot);
        b3.add(senddoc);
        b3.add(shakeWin);
        b1.add(send);
        b2.add(quit);


        jp5.setLayout(new BoxLayout(jp5, BoxLayout.Y_AXIS));
        b3.setBorder(BorderFactory.createEmptyBorder(20, 0, -30, 0));
        b1.setBorder(BorderFactory.createEmptyBorder(0, 0, -30, 0));
        b2.setBorder(BorderFactory.createEmptyBorder(0, 0, -20, 0));

        jp5.add(b3);
        jp5.add(b1);
        jp5.add(b2);

        jp7.setLayout(new BoxLayout(jp7, BoxLayout.X_AXIS));
        jp5.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        jp7.add(jp4);
        jp7.add(jp5);


        jp6.setLayout(new BoxLayout(jp6, BoxLayout.Y_AXIS));
        jp0.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jp3.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jp7.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        jp6.add(jp0);
        jp6.add(jp3);
        jp6.add(jp7);

        Dimension buttonSize = new Dimension(90, 35);
        send.setPreferredSize(buttonSize);
        quit.setPreferredSize(buttonSize);


        jp0.setOpaque(false);
        jp1.setOpaque(false);
        jp3.setOpaque(false);
        jp4.setOpaque(false);
        jp5.setOpaque(false);
        jp6.setOpaque(false);
        jp7.setOpaque(false);
        jp8.setOpaque(false);
        jp10.setOpaque(false);
        b1.setOpaque(false);
        b2.setOpaque(false);
        b3.setOpaque(false);

        this.setContentPane(new BackgroundPanel("src\\main\\java\\背景\\bz4.jpg"));
        this.add(jp6);
        this.user = user;
        this.username = userName;
        this.client = new Client(ip, Integer.parseInt(port), this, userName, true);
        try {
            client.startShutdownHook();
        } catch (IOException e1) {
            // TODO 自动生成的 catch 块
            e1.printStackTrace();
        }
        this.setTitle("私聊");
        this.setSize(650, 425);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
        client.inputDivide();

//        StyleConstants.setFontSize(style, 18);

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // TODO 自动生成的方法存根
                super.windowClosing(e);
                setVisible(false);

            }

        });
        style = msg.addStyle("MyStyle", null);
        style2 = msg.addStyle("MyStyle2", null);
        style3 = msg.addStyle("MyStyle3", null);
        StyleConstants.setForeground(style, Color.gray);
        StyleConstants.setForeground(style2, Color.blue);
        StyleConstants.setForeground(style3, Color.red);
    }

    public static void main(String[] args) {
        // TODO 自动生成的方法存根
        new PrivateUI("localhost", "1234", null, null);
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
        if (e.getSource() == send) {
            if (sendmsg.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "不能发送空白哦~", "提示消息", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    client.output(sendmsg.getText(), username, user, false);
                    sendmsg.setText("");
                } catch (IOException e1) {
                    // TODO 自动生成的 catch 块
                    JOptionPane.showMessageDialog(null, "发送失败", "提示消息", JOptionPane.WARNING_MESSAGE);
                }
            }
        } else if (e.getSource() == screenshot) {
            new ScreenCapture(client, username, user);

        } else if (e.getSource() == quit) {
            this.setVisible(false);
        } else if (e.getSource() == senddoc) {

            Point location = this.getLocation();
            int x = location.x;
            int y = location.y;
            new FileOutputUI(x + 680, y + 260, client, username, user);

        } else if (e.getSource() == shakeWin) {
            try {
                client.output(null, username, user, true);
                ShakeWin.shakeWindow(this);
            } catch (IOException e1) {
                // TODO 自动生成的 catch 块
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                // TODO 自动生成的 catch 块
                e1.printStackTrace();
            }
        } else if (e.getSource() == clear) {
            msg.setText("");
        } else if (e.getSource() == video) {
            int port = ThreadLocalRandom.current().nextInt(10000, 60000);
            try{
                new VideoServer(port);
            }catch (Exception ew) {}
            client.outputVideo(port, username, user);
        }

    }
}


