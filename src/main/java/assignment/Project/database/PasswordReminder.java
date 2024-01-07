package assignment.Project.database;
import assignment.Project.msgclass.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswordReminder {

	public PasswordReminder() {
		// TODO 自动生成的构造函数存根
	}
	public static Messagepackage check(String user) {
		Messagepackage msg=new Messagepackage();
		Connection conn=DatabaseConnect.getConn();
		try {         
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? ");
			stmt.setString(1, user);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				msg.setAnswer(rs.getString("answer"));
				msg.setQuestion(rs.getString("question"));
				msg.setPassword(rs.getString("password"));
				return msg;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		msg.setProblem("NULL");
		return msg;
	}
}
