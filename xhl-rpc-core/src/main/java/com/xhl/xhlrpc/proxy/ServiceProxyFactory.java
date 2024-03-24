package com.xhl.xhlrpc.proxy;

import com.xhl.xhlrpc.RpcApplication;

import java.lang.reflect.Proxy;

/**
 * 服务代理工厂（用于创建代理对象）
 *
 * @author daiyifei
 */
public class ServiceProxyFactory {

    /**
     * 根据服务类获取代理对象
     *
     * @param serviceClass
     * @param <T>
     * @return
     */
    public static <T> T getProxy(Class<T> serviceClass) {
        // 判断是否mock
        if (RpcApplication.getRpcConfig().isMock()) {
            System.out.println("mock调用");
            return getMockProxy(serviceClass);
        }

        System.out.println("getProxy");
        System.out.println(serviceClass);
        System.out.println(serviceClass.getName());

        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy());
    }

    /**
     * 根据服务类获取mock代理对象
     *
     * @param serviceClass
     * @param <T>
     * @return
     */
    public static <T> T getMockProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass}
                , new MockServiceProxy());

    }
}