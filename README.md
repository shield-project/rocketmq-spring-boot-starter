rocketmq-spring-boot-starter 2.0.0-RELEASE
===================================

[chinese](https://github.com/shieldproject/rocketmq-spring-boot-starter/blob/master/README.md)

* 支持jdk版本为1.8
* SpringBoot版本为1.5.6
* maven仓库地址[新aliyun maven](https://maven.aliyun.com/repository/public)

### 如何运行rocketmq-spring-boot-starter

* 构建项目

```shell
	mvn install
```

* 添加依赖:


```xml
    <dependency>
        <groupId>org.shieldproject.rocketmq</groupId>
        <artifactId>rocketmq-spring-boot-starter</artifactId>
        <version>2.0.0-RELEASE</version>
    </dependency>
```

* 添加必要配置application.yml

```yml
	spring:
	  mq:
	    rocket:
	      config:
	        namesrv-addr: 192.168.2.26:9876
	        instance-name: test
```
config配置可以参考rocketMQ自带的config配置进行补充。

* 启用MQConfig

	在Application class头部添加<b>@EnableMQConfiguration</b>注解，使其框架自动处理messageProducer以及messageConsumer。

```java
	@SpringBootApplication
	@EnableRocketMQConfiguration
	public class Application {
	    public static void main(String[] args) {
	        SpringApplication.run(Application.class, args);
	    }
	}
```

* 配置MQConsumer listener

方法参数选配：框架自动识别参数进行注入
*   String msg  注入单条消息，produce发送的单条消息
*   List\<String\> msgs   注入多条消息，produce发送的是多条消息
*   ConsumeConcurrentlyContext  注入ConsumeConcurrentlyContext如果当前消费模式是Concurrently
*   ConsumeOrderlyContext   注入ConsumeConcurrentlyContext如果当前消费模式是Concurrently

返回值选配
*   void    如果无需处理事务回滚操作(自动处理事务)
*   ConsumeConcurrentlyStatus   如果消费模式是concurrently
*   ConsumeOrderlyStatus        如果消费模式是Orderly

```java
    @RocketMQListener(instance = "testfor1",
            topic = "test", tags = "test",
            consumeFromWhere = ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET)
    public void consumeMessage(String msgs) {
        System.out.println(msgs + System.currentTimeMillis());
    }
```

* 注入MessageProducer

```java
public class ProducerController {
    @Autowired
    MQProducer mqProducer;

    @GetMapping("/pro")
    public String msg(String content) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        mqProducer.send(new Message("test", "test", content.getBytes(Charset.forName("UTF-8"))));
        return content;
    }
}
```

###	以上可参考test包。