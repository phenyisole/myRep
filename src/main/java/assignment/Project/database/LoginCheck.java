package assignment.Project.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginCheck {

	public LoginCheck() {
		// TODO 自动生成的构造函数存根
	}
	public static boolean check(String user,String pass) {
		Connection conn=DatabaseConnect.getConn();
		try {         
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
			stmt.setString(1, user);
			stmt.setString(2, pass);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return true; // 找到匹配项
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; // 未找到匹配项
	}
}
