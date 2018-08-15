package org.shoper.test;

import com.ruiqi.commons.mq.annotation.EnableMQConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ShawnShoper
 * @date 2018/7/30 11:33
 */
@SpringBootApplication
@EnableMQConfiguration
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
