package assignment.Project.function;

import assignment.Project.client.Client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;

public class ScreenCapture {
    private JFrame frame;
    private Rectangle selection;
    private Robot robot;
    private boolean isDragging;
    Client client;
    String sendUser;
    String beSendUser;
    public ScreenCapture(Client client,String sendUser,String beSendUser) {
        this.client=client;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        frame = new JFrame();
        frame.setUndecorated(true);
        frame.setBackground(new Color(0, 0, 0, 0));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setAlwaysOnTop(true);
        frame.setOpacity((float) 0.5);

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!isDragging) {
                    isDragging = true;
                    selection = new Rectangle(e.getX(), e.getY(), 0, 0);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isDragging) {
                    isDragging = false;
                    frame.dispose();
                    if(selection.height!=0&&selection.width!=0)
                        captureScreen(selection);
                    else {
                        JOptionPane.showMessageDialog(null, "请框选方形!","提示消息",JOptionPane.WARNING_MESSAGE);

                    }
                }
            }
        });

        frame.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDragging) {
                    int x = Math.min(selection.x, e.getX());
                    int y = Math.min(selection.y, e.getY());
                    int width = Math.abs(selection.x - e.getX());
                    int height = Math.abs(selection.y - e.getY());

                    selection.setBounds(x, y, width, height);
                    frame.repaint();
                }

            }
        });

        frame.getContentPane().add(new JPanel() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 0, 0, 50));
                g.fillRect(0, 0, getWidth(), getHeight());
                if (selection != null) {
                    g.setColor(new Color(255, 0, 0, 128)); // 设置红色的透明度为128
                    g.drawRect(selection.x, selection.y, selection.width, selection.height);
                    g.fillRect(selection.x, selection.y, selection.width, selection.height);
                }
            }
        });
        this.beSendUser=beSendUser;
        this.sendUser=sendUser;
        frame.setVisible(true);
    }

    private void captureScreen(Rectangle area) {
        try {
            BufferedImage screenshot = robot.createScreenCapture(area);
            String Path = System.getProperty("user.home") + "\\Desktop\\聊天室downloads\\ScreenCapture";
            String ss = String.valueOf(System.currentTimeMillis());
            File outputFile = new File(Path, ss + ".png");

            outputFile.getParentFile().mkdirs();

            ImageIO.write(screenshot, "png", outputFile);
            new ScreenShotUI(Path+"\\"+ss+".png",client,sendUser,beSendUser);
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        new ScreenCapture(null);
    }
}