package com.xhl.example.consumer;


import com.xhl.example.common.model.User;
import com.xhl.example.common.service.UserService;
import com.xhl.xhlrpc.proxy.ServiceProxyFactory;

/**
 * 消费者示例
 */
public class EasyConsumerExample {

    public static void main(String[] args) {

        // 使用动态代理获取 UserService 的实现类对象
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);


        System.out.println("代理对象：" + userService);
        System.out.println("开始------------------------");

        User user = new User();
        user.setName("xhl");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
            System.out.println(newUser);
        } else {
            System.out.println("user==null");
        }

    }
}
