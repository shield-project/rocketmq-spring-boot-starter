package org.shieldproject.rocketmq.test;

import org.shieldproject.rocketmq.annotation.EnableRocketMQConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ShawnShoper
 * @date 2018/7/30 11:33
 */
@SpringBootApplication
@EnableRocketMQConfiguration
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
