package assignment.Project.function;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {
    /**
    *
    */
   private static final long serialVersionUID = 1L;
   Image background;

   public BackgroundPanel(String imagePath) {
       try {
           background = ImageIO.read(new File(imagePath));
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   @Override
   protected void paintComponent(Graphics g) {
       super.paintComponent(g);
       g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
   }


}