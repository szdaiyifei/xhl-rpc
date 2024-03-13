package com.xhl.xhlrpc.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.xhl.xhlrpc.model.RpcRequest;
import com.xhl.xhlrpc.model.RpcResponse;
import com.xhl.xhlrpc.serializer.JdkSerializer;
import com.xhl.xhlrpc.serializer.Serializer;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author daiyifei
 */

/**
 * 服务代理（JDK 动态代理）
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
        Serializer serializer = new JdkSerializer();

        // 构造请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        System.out.println("代理请求：" + rpcRequest);
        System.out.println("服务名称：" + rpcRequest.getServiceName());
        System.out.println("方法名称："+ rpcRequest.getMethodName());
        System.out.println("参数类型："+ Arrays.toString(rpcRequest.getParameterTypes()));
        System.out.println("参数列表："+ Arrays.toString(rpcRequest.getArgs()));

        try {
            // 序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            // 发送请求
            // todo 注意，这里地址被硬编码了（需要使用注册中心和服务发现机制解决）
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes)
                    .execute()) {
                byte[] result = httpResponse.bodyBytes();
                // 反序列化
                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
                System.out.println("拿到结果" + rpcResponse.getData());
                return rpcResponse.getData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}