package assignment.Project.database;

import java.sql.*;

public class RegisterAccount {

	public RegisterAccount() {
		// TODO 自动生成的构造函数存根
	}
	public static boolean check(String user,String pass,String question,String answer) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		conn=DatabaseConnect.getConn();
		String sqlCheck = "SELECT * FROM users WHERE username = ?";
		try {
			pstmt = conn.prepareStatement(sqlCheck);
			pstmt.setString(1, user);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				// 如果用户名已存在，返回false
				return false;
			} else {
				// 否则，插入新的用户名和密码
				String sqlInsert = "INSERT INTO users (username, password,question,answer) VALUES (?, ?,?,?)";
				pstmt = conn.prepareStatement(sqlInsert);
				pstmt.setString(1, user);
				pstmt.setString(2, pass);
				pstmt.setString(3, question);
				pstmt.setString(4, answer);
				pstmt.executeUpdate();
				return true;
			}
		} catch (SQLException e) { 
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		return false;

	}

}
