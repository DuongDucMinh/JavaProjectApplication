package com.example.javafxapp;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SimpleHttpServer {
    public static void main(String[] args) throws IOException {
        // Tạo server tại cổng 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // Tạo context
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String path = "local_html" + exchange.getRequestURI().getPath();

                //Trỏ tới thư mục local_html
                if (path.equals("local_html/")) {
                    path = "local_html/index.html";
                }

                Path filePath = Paths.get(path);

                if (Files.exists(filePath)) {
                    byte[] content = Files.readAllBytes(filePath);
                    // Đảm bảo rằng server trả lại đúng cho HTML và CSS
                    String contentType = path.endsWith(".css") ? "text/css" : "text/html";
                    exchange.getResponseHeaders().set("Content-Type", contentType + "; charset=UTF-8");
                    exchange.sendResponseHeaders(200, content.length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(content);
                    os.close();
                } else {
                    String notFound = "<h1>404 Not Found</h1>";
                    exchange.sendResponseHeaders(404, notFound.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(notFound.getBytes());
                    os.close();
                }
                exchange.close();
            }
        });

        // Khởi động server
        server.start();
        System.out.println("Server running at http://localhost:8000/");
    }
}
