package web;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Map;

public class SimpleHttpServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/login", new LoginHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class LoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                // 从请求中获取用户名和密码
                Map<String, String> parameters = getParameters(exchange);
                String username = parameters.get("username");
                String password = parameters.get("password");

                // 在这里进行用户名和密码的验证
                if (isValidUser(username, password)) {
                    // 如果用户名和密码正确，返回登录成功的响应
                    String response = "<html>\n" +
                            "<body>\n" +
                            "<h1>Contact developers<br></h1>\n" +
                            "<h2>Mailbox:1163005913@qq.com<br></h2>\n" +
                            "<h2>source code:</h2>\n" +
                            "</body>\n" +
                            "</html>";
                    sendResponse(exchange, 200, response);
                } else {
                    // 如果用户名和密码不正确，返回登录失败的响应
                    String response = "<html><body><h1>Login Failed</h1></body></html>";
                    sendResponse(exchange, 401, response);
                }
            } else {
                // 如果不是POST请求，返回405 Method Not Allowed
                sendResponse(exchange, 405, "Method Not Allowed");
            }
        }

        private Map<String, String> getParameters(HttpExchange exchange) {
            // 从请求中获取参数
            // 这里需要根据具体的请求格式来解析参数
            // 这里只是一个简单的示例，实际情况可能更复杂
            return Collections.emptyMap();
        }

        private boolean isValidUser(String username, String password) {
            // 在这里进行用户名和密码的验证
            // 可以连接数据库进行验证，或者使用其他方式验证
            return true; // 这里只是一个简单的示例，实际情况需要根据具体需求来实现
        }

        private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
            exchange.sendResponseHeaders(statusCode, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}