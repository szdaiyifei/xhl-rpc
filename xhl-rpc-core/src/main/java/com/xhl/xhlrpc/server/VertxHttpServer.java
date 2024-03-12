package com.xhl.xhlrpc.server;

import io.vertx.core.Vertx;

/**
 * @author daiyifei
 */
public class VertxHttpServer implements HttpServer {
    @Override
    public void doStart(int port) {
        //创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();

        // 创建 HTTP 服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();



        // 监听端口并处理请求
        server.requestHandler(new HttpServerHandler());
//        server.requestHandler(request -> {
//            // 处理 HTTP 请求
//            System.out.println("Received request: " + request.method() + " " + request.uri());
//
//            // 发送 HTTP 响应
//            request.response().putHeader("Content-Type", "text/plain")
//                    .end("Hello from Vert.x HTTP server");
//        });

        // 启动 HTTP 服务器并监听指定端口
        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("HTTP server started on port " + port);
            } else {
                System.out.println("Failed to start HTTP server" + result.cause());
            }
        });


    }
}
