package com.xhl.xhlrpc.proxy;

import com.github.javafaker.Faker;
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

    private static final Faker faker = new Faker();

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

        return getDefaultObject(methodReturnType);
    }

    /**
     * 生成指定类型的默认值对象
     *
     * @param type
     * @return
     */
    private Object getDefaultObject(Class<?> type) {
        // 基本类型
        if (type.isPrimitive()) {
            if (type == boolean.class) {
                return false;
            } else if (type == char.class) {
                return '\u0000';
            } else if (type == byte.class) {
                return (byte) 0;
            } else if (type == short.class) {
                return (short) 0;
            } else if (type == int.class) {
                return 0;
            } else if (type == long.class) {
                return 0L;
            } else if (type == float.class) {
                return 0.0f;
            } else if (type == double.class) {
                return 0.0d;
            }
        } else {
            // 为字符串类型提供伪造数据
            if (type == String.class) {
                // 伪造的（随机的）单词
                return faker.lorem().word();
            }
            // 为日期类型提供伪造数据
            else if (type == java.util.Date.class) {
                return faker.date().birthday();
            }
            // todo 添加更多对象类型的默认值生成逻辑

        }
        // 对于无法识别的类型，返回 null
        return null;
    }
}
