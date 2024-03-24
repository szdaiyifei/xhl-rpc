package com.xhl.example.provider;


import com.xhl.example.common.service.UserService;
import com.xhl.xhlrpc.RpcApplication;
import com.xhl.xhlrpc.config.RegistryConfig;
import com.xhl.xhlrpc.config.RpcConfig;
import com.xhl.xhlrpc.model.ServiceMetaInfo;
import com.xhl.xhlrpc.registry.LocalRegistry;
import com.xhl.xhlrpc.registry.Registry;
import com.xhl.xhlrpc.registry.RegistryFactory;
import com.xhl.xhlrpc.server.HttpServer;
import com.xhl.xhlrpc.server.VertxHttpServer;

/**
 * 服务提供者实例
 *
 * @author daiyifei
 */
public class ProviderExample {
    public static void main(String[] args) {
        // 初始化RPC框架
        RpcApplication.init();

        System.out.println(RpcApplication.getRpcConfig());
//        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
        // 注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());

    }
}
