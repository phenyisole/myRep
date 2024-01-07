package assignment.Project.video;



import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class VideoServer {
    static JFrame frame = new JFrame();
    public VideoServer(int port){
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setVisible(true);
        running(port);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // 在 UI 关闭时执行销毁操作
                frame.dispose();
            }
        });
    }

    public static void main(String[] args) {

        running(12345);
    }


	public static void running(int port) {
        new Thread(() -> {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    while (true) {
                        VideoStream vs = (VideoStream) ois.readObject();
                        displayVideo(vs.getBy());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void displayVideo(byte[] videoData) {
        try {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(videoData));
            SwingUtilities.invokeLater(() -> {
                frame.getContentPane().removeAll(); // Clear previous content
                frame.getContentPane().add(new JLabel(new ImageIcon(img)));
                frame.pack();
                frame.repaint(); // Refresh the frame
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
