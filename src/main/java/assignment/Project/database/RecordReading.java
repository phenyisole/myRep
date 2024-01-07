package assignment.Project.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JScrollBar;
import javax.swing.text.BadLocationException;

import assignment.Project.client.ClientUI;

public class RecordReading {
	ClientUI clientUI;
	public RecordReading(ClientUI clientUI) throws BadLocationException {
		// TODO 自动生成的构造函数存根
		this.clientUI=clientUI;
		read();
	}
	public void read() throws BadLocationException {
		try {
            Connection connection = DatabaseConnect.getConn();
            String sql = "SELECT user, content, time FROM chat";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                String user = result.getString("user");
                String content = result.getString("content");
                String time = result.getString("time");

                clientUI.docs.insertString(clientUI.docs.getLength(), user+"  ", clientUI.style3);
				clientUI.docs.insertString(clientUI.docs.getLength(),time+"\n", clientUI.style);
				if(user.equals(clientUI.userName.getText()))
					clientUI.docs.insertString(clientUI.docs.getLength(),"  "+content + "\n", clientUI.style2);
				else
					clientUI.docs.insertString(clientUI.docs.getLength(),"  "+content + "\n", null);
//				Thread.sleep((20));
				JScrollBar verticalBar = clientUI.scrollPane2.getVerticalScrollBar();
				verticalBar.setValue(verticalBar.getMaximum());
            }

            result.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error in database connection: " + e.getMessage());
        }
	}
}
