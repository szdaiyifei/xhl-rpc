package com.xhl.xhlrpc.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * mock 服务代理（jdk 动态代理）
 *
 * @author daiyifei
 */
@Slf4j
public class MockServiceProxy implements InvocationHandler {
    /**
     * 调用带俩
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 根据方法的返回值类型，生成特定的默认值对象
        Class<?> methodReturnType = method.getReturnType();
        log.info("mork invoke：{}", method.getName());

        return getDeaultObject(methodReturnType);
    }

    /**
     * 生成指定类型的默认值对象
     *
     * @param type
     * @return
     */
    private Object getDeaultObject(Class<?> type) {
        // 基本类型
        if (type.isPrimitive()) {
            if (type == boolean.class) {
                return false;
            } else if (type == short.class) {
                return (short) 0;

            } else if (type == int.class) {
                return 0;

            } else if (type == long.class) {
                return 0L;
            }
        }
        // 对象类型
        return null;
    }
}
