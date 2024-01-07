package assignment.Project.video;


import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class VideoClient {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    public static void main(String[] args) {
//        running(12345,"localhost");
    }
    public VideoClient(int port,String ip){
        running(port,ip);
    }
    public void running(int port,String ip) {
        try {
            Socket socket = new Socket(ip,port);
            new Thread(() -> {
                try {

                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

                    Webcam webcam = Webcam.getDefault();
                    webcam.setViewSize(new Dimension(WIDTH, HEIGHT));
                    webcam.open();

                    // 创建窗口显示视频流
                    JFrame frame = new JFrame("Video Stream");
//                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setSize(WIDTH, HEIGHT);

                    JLabel label = new JLabel();
                    frame.getContentPane().add(label, BorderLayout.CENTER);
                    frame.setVisible(true);
                    frame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            // 在 UI 关闭时执行销毁操作
                            frame.dispose();
                        }
                    });
                    while (true) {
                        // 从摄像头捕获图像
                        BufferedImage image = webcam.getImage();

                        // 将视频流数据放入新的VideoStream对象
                        VideoStream videoStream = new VideoStream();
                        videoStream.setBy(imageToBytes(image));

                        // 发送视频流
                        outputStream.writeObject(videoStream);

                        // 在窗口显示视频流
                        label.setIcon(new ImageIcon(image));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] imageToBytes(BufferedImage image) throws IOException {
        // 将BufferedImage对象转换为byte数组
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        return baos.toByteArray();
    }
}
