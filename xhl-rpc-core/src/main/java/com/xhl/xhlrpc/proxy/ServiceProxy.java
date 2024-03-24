package com.xhl.xhlrpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.xhl.xhlrpc.RpcApplication;
import com.xhl.xhlrpc.config.RpcConfig;
import com.xhl.xhlrpc.constant.RpcConstant;
import com.xhl.xhlrpc.model.RpcRequest;
import com.xhl.xhlrpc.model.RpcResponse;
import com.xhl.xhlrpc.model.ServiceMetaInfo;
import com.xhl.xhlrpc.registry.Registry;
import com.xhl.xhlrpc.registry.RegistryFactory;
import com.xhl.xhlrpc.serializer.Serializer;
import com.xhl.xhlrpc.serializer.SerializerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author daiyifei
 */

/**
 * 服务代理（JDK 动态代理）
 *
 * @author daiyifei
 */
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());
        System.out.println("序列化器：" + serializer);

        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        System.out.println("代理请求：" + rpcRequest);
        System.out.println("服务名称：" + rpcRequest.getServiceName());
        System.out.println("方法名称：" + rpcRequest.getMethodName());
        System.out.println("参数类型：" + Arrays.toString(rpcRequest.getParameterTypes()));
        System.out.println("参数列表：" + Arrays.toString(rpcRequest.getArgs()));

        try {
            // 序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            // 发送请求
            // 从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }
            // 暂时先取第一个
            ServiceMetaInfo selectedServiceMetaInfo = serviceMetaInfoList.get(0);

            // 发送请求
            try (HttpResponse httpResponse = HttpRequest.post(selectedServiceMetaInfo.getServiceAddress())
                    .body(bodyBytes)
                    .execute()) {
                byte[] result = httpResponse.bodyBytes();
                // 反序列化
                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
                return rpcResponse.getData();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}