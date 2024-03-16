package com.xhl.xhlrpc.serializer;

import com.xhl.xhlrpc.spi.SpiLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * 序列化器工厂（用户获得序列化器对象）
 *
 * @author daiyifei
 */
public class SerializerFactory {

    /**
     * 序列化器映射(键：序列化器类型，值：序列化器对象)
     */
//    public static final Map<String, Serializer> KEY_SERIALIZER_MAP =
//            new HashMap<>(){
//                {
//                    put(SerializerKeys.JDK,new JdkSerializer());
//                    put(SerializerKeys.JSON,new JsonSerializer());
//                    put(SerializerKeys.HESSIAN,new HessianSerializer());
//                    put(SerializerKeys.KRYO,new KryoSerializer());
//
//                }
//            };
    static {
        SpiLoader.load(Serializer.class);
    }

    /**
     * 默认序列化器 jdk
     */
    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    /**
     * 获取序列化器实例
     *
     * @param key
     * @return
     */
    public static Serializer getInstance(String key) {
        Object instance = SpiLoader.getInstance(Serializer.class, key);
        if (instance == null) {
            return DEFAULT_SERIALIZER;
        }
        return (Serializer) instance;
    }


}
