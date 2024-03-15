package com.xhl.example.consumer;


import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.setting.yaml.YamlUtil;
import com.xhl.xhlrpc.config.RpcConfig;
import com.xhl.xhlrpc.utils.ConfigUtils;

import java.util.Map;

/**
 * 服务消费者实例
 *
 * @author daiyifei
 */
public class ConsumerExample {

    public static void main(String[] args) {


        // 加载YAML文件为Map
        Map<String, Object> yamlMap = YamlUtil.loadByPath("application.yaml");
//        JSONObject jsonObject =
//                JSONUtil.parseObj(yamlMap);
//        System.out.println(jsonObject);
//
//
//        RpcConfig bean = JSONUtil.toBean(jsonObject.getJSONObject("rpc"), RpcConfig.class);
//
//        // 现在你可以基于yamlMap进行操作或将其转换为特定对象
//        System.out.println(bean);
        // 测试配置文件读取
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpc);
    }
}
