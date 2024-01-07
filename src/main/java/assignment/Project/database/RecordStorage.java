package assignment.Project.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RecordStorage {
	
	public static void RecordStorages(String user,String content,String time) {
		 try {
	            Connection connection = DatabaseConnect.getConn();
	            String sql = "INSERT INTO chat (user, content, time) VALUES (?, ?, ?)";

	            PreparedStatement statement = connection.prepareStatement(sql);
	            statement.setString(1, user);
	            statement.setString(2,content);
	            statement.setString(3, time);

	            statement.executeUpdate();
	            DatabaseConnect.closePs(statement);
	            DatabaseConnect.closeConn(connection);
	        } catch (SQLException e) {
	            System.out.println("Error in database connection: " + e.getMessage());
	        }
	}

}
