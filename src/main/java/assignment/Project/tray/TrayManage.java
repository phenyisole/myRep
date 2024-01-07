package assignment.Project.tray;
import assignment.Project.login.LoginUI;

import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import javax.swing.*;

public class TrayManage {//implements MouseListener,WindowListener{
	SystemTray systray;
	TrayIcon tray;
	Image image =new ImageIcon("src\\main\\java\\组件\\logo3.png").getImage();
	JFrame login;

	public TrayManage(JFrame login) {
		this.login=login;
	}
	public void closeTray() {
	    SystemTray.getSystemTray().remove(tray);
	}
	public void addTray() {
		if(SystemTray.isSupported()) {
			systray=SystemTray.getSystemTray();
			tray=new TrayIcon(image);
			try {
				systray.add(tray);
				tray.setImageAutoSize(true);
				PopupMenu menu = new PopupMenu();
				MenuItem exitItem = new MenuItem("Log Out");
				MenuItem openItem = new MenuItem("developers");
				openItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							Desktop.getDesktop().browse(new URI("https://www.example.com"));
						} catch (Exception e22) {
							e22.printStackTrace();
						}
					}
				});
				exitItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						login.dispose();
						new LoginUI();
						closeTray();
					}
				});
				menu.add(openItem);
				menu.add(exitItem);
				tray.setPopupMenu(menu);
			} catch (AWTException e) {
				e.printStackTrace();
			}
			login.addWindowListener(new WindowListener() {

				@Override
				public void windowOpened(WindowEvent e) {
					// TODO 自动生成的方法存根
					
				}

				@Override
				public void windowClosing(WindowEvent e) {
					// TODO 自动生成的方法存根
	
				}

				@Override
				public void windowClosed(WindowEvent e) {
					// TODO 自动生成的方法存根
	
				}

				@Override
				public void windowIconified(WindowEvent e) {
					// TODO 自动生成的方法存根
//					login.dispose();
				}

				@Override
				public void windowDeiconified(WindowEvent e) {
					// TODO 自动生成的方法存根
					
				}

				@Override
				public void windowActivated(WindowEvent e) {
					// TODO 自动生成的方法存根
					
				}

				@Override
				public void windowDeactivated(WindowEvent e) {
					// TODO 自动生成的方法存根
					
				}
				
			});
			tray.addMouseListener(new MouseListener(){

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO 自动生成的方法存根
					if(e.getClickCount()>=1) {
						login.setExtendedState(JFrame.NORMAL);
						login.setVisible(true);
						
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO 自动生成的方法存根
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO 自动生成的方法存根
					
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO 自动生成的方法存根
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO 自动生成的方法存根
					
				}
				
			});
				
		}
	}
}
