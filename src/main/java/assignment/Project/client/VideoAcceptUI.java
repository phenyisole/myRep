package assignment.Project.client;

import assignment.Project.video.VideoClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;

public class VideoAcceptUI extends JFrame implements ActionListener
{
    private static final long serialVersionUID = 1L;
    public JLabel text;
    JButton certain,quit;
    Client client;
    String path;
    JPanel jp1,jp2;
    PrivateUI clientui;
    String sendUser;
    int port;
    String beSendUser;
    public VideoAcceptUI(String user, int port,PrivateUI clientui) {
        // TODO 自动生成的构造函数存根
        text=new JLabel("是否同意来自"+user+"的视频");
        text.setFont(new Font("黑体",Font.PLAIN,15));
        certain=new JButton("确定");
        quit=new JButton("取消");
        this.clientui=clientui;

        quit.addActionListener(this);
        certain.addActionListener(this);

        jp1=new JPanel();
        jp2=new JPanel();
        jp1.add(certain);
        jp1.add(quit);
        jp2.add(text);

        this.port=port;
        this.setSize(260,120);

        int x = clientui.getX() + clientui.getWidth() / 2;
        int y = clientui.getY() + clientui.getHeight() / 2;
        this.setLocation(x - this.getWidth() / 2, y - this.getHeight() / 2);
        this.setLayout(new GridLayout(2,1));
        this.add(jp2);
        this.add(jp1);
        this.setVisible(true);
//		this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO 自动生成的方法存根
        if(e.getSource()==quit) {
            this.dispose();
        }else if(e.getSource()==certain) {
            new VideoClient(port, clientui.ip);
            this.dispose();
        }
    }
    public static void main(String[] args) {
        // TODO 自动生成的方法存根

    }

}
