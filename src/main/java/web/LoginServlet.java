package web;

import assignment.Project.database.DatabaseConnect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username1 = request.getParameter("username");
        String password1 = request.getParameter("password");

        try {
            Connection conn = DatabaseConnect.getConn();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username1);
            stmt.setString(2, password1);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                response.sendRedirect("web2.html");
            } else {
                response.sendRedirect("web2.html");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}