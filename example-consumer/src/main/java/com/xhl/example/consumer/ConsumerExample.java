package com.xhl.example.consumer;


import com.xhl.example.common.model.User;
import com.xhl.example.common.service.UserService;
import com.xhl.xhlrpc.proxy.ServiceProxyFactory;

/**
 * 服务消费者实例
 *
 * @author daiyifei
 */
public class ConsumerExample {

    public static void main(String[] args) {

//        // 测试配置文件读取
//        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
//        System.out.println(rpc);
        // 获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("xhl");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user==null");
        }
        long number = userService.getNumber();
        System.out.println(number);

        String userName = userService.getName();
        System.out.println(userName);


    }
}
