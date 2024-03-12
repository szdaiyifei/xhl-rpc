package com.xhl.example.provider;

import com.xhl.example.common.service.UserService;
import com.xhl.xhlrpc.registry.LocalRegistry;
import com.xhl.xhlrpc.server.HttpServer;
import com.xhl.xhlrpc.server.VertxHttpServer;

/**
 * 服务提供者示例
 */
public class EasyProviderExample {

    public static void main(String[] args) {

        // 注册服务
        LocalRegistry.register(UserService.class.getName(),UserServiceImpl.class);

        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);

    }
}