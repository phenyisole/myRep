package assignment.Project.client;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileOutputUI extends JFrame implements ActionListener{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public JLabel text;
	JTextField input;
	JButton certain,quit;
	Client client;
	JPanel jp1;
	String sendUser;
	String beSendUser;
	public FileOutputUI(int x,int y,Client client,String sendUser,String beSendUser) {
		// TODO 自动生成的构造函数存根
		text=new JLabel("请输入文件路径或将其拖入");
		text.setFont(new Font("楷体",Font.PLAIN,15));
		input=new JTextField(80);
		certain=new JButton("确定");
		quit=new JButton("取消");

		this.client=client;

		quit.addActionListener(this);
		certain.addActionListener(this);

		Dimension buttonSize = new Dimension(100, 30);
//        certain.setPreferredSize(buttonSize);
//        quit.setPreferredSize(buttonSize);
		input.setPreferredSize(buttonSize);

		jp1=new JPanel();
		jp1.add(certain);
		jp1.add(quit);

		input.setTransferHandler(new TransferHandler() {
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean canImport(TransferSupport support) {
				return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
			}

			@Override
			public boolean importData(TransferSupport support) {
				try {
					Transferable transferable = support.getTransferable();
					@SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
					if (droppedFiles.size() > 0) {
						File file = droppedFiles.get(0);
						input.setText(file.getAbsolutePath());
					}
					return true;
				} catch (Exception ex) {
					ex.printStackTrace();
					return false;
				}
			}
		});//直接复制的 看不懂
		this.beSendUser=beSendUser;
		this.sendUser=sendUser;
		this.setLayout(new GridLayout(3,1));
		this.add(text);
		this.add(input);
		this.add(jp1);
		this.setLocation(x,y);
		this.setUndecorated(true);
		this.setSize(210,92);
		this.setVisible(true);
		this.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource()==quit) {
			this.dispose();
		}else if(e.getSource()==certain) {
			try {
				client.outputDoc(input.getText(),sendUser,beSendUser);
//				client.output2(input.getText());
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			this.dispose();
		}
	}


}
