package com.arronzhu.rpc.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author arronzhu
 * @date 2018/10/31
 * @description
 */
@SpringBootApplication(scanBasePackages = {"com.arronzhu.rpc"})
public class Application {
    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
        ServiceRegister.register();
    }
}
