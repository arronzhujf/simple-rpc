package com.arronzhu.rpc.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author arronzhu
 * @date 2018/10/25
 * @description
 */
@SpringBootApplication(scanBasePackages = {"com.arronzhu.rpc"})
public class Application {

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
        ConsumerTest.add();
    }
}
