package com.xhl.xhlrpc.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

/**
 * @author daiyifei
 * 配置工具类
 * 提供重载方法可以给未来的扩展留下空间
 */
public class ConfigUtils {


    /**
     * 加载配置对象
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix) {
        return loadConfig(tClass, prefix, "");
    }

    public  static <T> T loadConfig(Class<T> tClass, String prefix, String environment) {

        StringBuilder configFileBuilder = new StringBuilder("application");
        if(StrUtil.isNotBlank(environment)){
            configFileBuilder.append("-").append(environment);
        }
        configFileBuilder.append(".properties");
        Props props = new Props(configFileBuilder.toString());
        System.out.println(props);
        return props.toBean(tClass,prefix);
    }
}
