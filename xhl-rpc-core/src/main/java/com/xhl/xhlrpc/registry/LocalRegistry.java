package com.xhl.xhlrpc.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalRegistry {
    /**
     * 注册信息存储
     */
    private static final Map<String,Class<?>> map = new ConcurrentHashMap<>();

    /**
     * 注册服务
     */
    public static void register(String serviceName,Class<?> implClazz){
        map.put(serviceName,implClazz);
    }

    /**
     * 获取服务
     */
    public static Class<?> get(String serviceName){
        return map.get(serviceName);
    }

    /**
     * 删除服务
     */

    public static void remove(String serviceName){
        map.remove(serviceName);
    }


}
