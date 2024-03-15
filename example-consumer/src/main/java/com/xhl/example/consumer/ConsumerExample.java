package com.xhl.example.consumer;


import com.xhl.xhlrpc.config.RpcConfig;
import com.xhl.xhlrpc.utils.ConfigUtils;

/**
 * 服务消费者实例
 *
 * @author daiyifei
 */
public class ConsumerExample {

    public static void main(String[] args) {

        // 测试配置文件读取¬
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpc);
    }
}
