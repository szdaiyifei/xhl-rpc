package com.xhl.xhlrpc.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.setting.dialect.Props;
import cn.hutool.setting.yaml.YamlUtil;
import com.xhl.xhlrpc.config.RpcConfig;

import java.util.Map;

/**
 * @author daiyifei
 * 配置工具类
 * 提供重载方法可以给未来的扩展留下空间
 */
public class ConfigUtils {


    /**
     * 加载配置对象，自动识别配置文件格式
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix) {
        return loadConfig(tClass, prefix, "");
    }

//    public  static <T> T loadConfig(Class<T> tClass, String prefix, String environment) {
//
//        StringBuilder configFileBuilder = new StringBuilder("application");
//        if(StrUtil.isNotBlank(environment)){
//            configFileBuilder.append("-").append(environment);
//        }
//        configFileBuilder.append(".properties");
//        Props props = new Props(configFileBuilder.toString());
//        System.out.println(props);
//        return props.toBean(tClass,prefix);
//    }
//
//

    public static <T> T loadConfig(Class<T> tClass, String prefix, String environment) {
        T config = loadFromProperties(tClass, prefix, environment);
        if (config != null) {
            return config;
        }
        config = loadFromYaml(tClass, prefix, environment);
        JSONObject parse = (JSONObject) JSONUtil.parse(config);
        T bean = JSONUtil.toBean(parse.getJSONObject(prefix), tClass);
        System.out.println(bean);
        return (T) bean;
    }

    private static <T> T loadFromProperties(Class<T> tClass, String prefix, String environment) {
        String baseName = "application" + (StrUtil.isNotBlank(environment) ? "-" + environment : "") + ".properties";
        if (FileUtil.exist(baseName)) {
            Props props = new Props(baseName);
            System.out.println(props);
            return props.toBean(tClass, prefix);
        }
        return null;
    }

    private static <T> T loadFromYaml(Class<T> tClass, String prefix, String environment) {
        String baseName = "application" + (StrUtil.isNotBlank(environment) ? "-" + environment : "") + ".yaml";
        if (FileUtil.exist(baseName)) {
            // 使用Hutool的YamlUtil读取YAML文件
            System.out.println("Loading YAML configuration from " + baseName);
            // 加载YAML文件为Map
            Map<String, Object> yamlMap = YamlUtil.loadByPath(baseName);
            JSONObject jsonObject =
                    JSONUtil.parseObj(yamlMap);

            System.out.println("jsonObject:" + jsonObject);
            return (T) jsonObject;
        }

        return null;
    }
}
