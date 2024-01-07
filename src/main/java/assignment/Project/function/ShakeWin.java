package assignment.Project.function;

import java.awt.Point;

import javax.swing.JFrame;
public class ShakeWin {

	public ShakeWin() {
		// TODO 自动生成的构造函数存根
	}
    public static void shakeWindow(JFrame frame) throws InterruptedException {
    	Point location = frame.getLocation();
    	int x = location.x;
    	int y = location.y;
    	for(int i=0;i<2;i++) {
    		frame.setLocation(x+5,y+5);
    		Thread.sleep(20);
    		frame.setLocation(x+5,y);
    		Thread.sleep(20);
    		frame.setLocation(x+5,y-5);
    		Thread.sleep(20);
    		frame.setLocation(x,y-5);
    		Thread.sleep(20);
    		frame.setLocation(x-5,y-5);
    		Thread.sleep(20);
    		frame.setLocation(x-5,y);
    		Thread.sleep(20);
    		frame.setLocation(x-5,y+5);
    		Thread.sleep(20);
    		frame.setLocation(x,y+5);
    		Thread.sleep(20);
    	}

    }
}
