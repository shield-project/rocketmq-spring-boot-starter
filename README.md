spring-boot-starter-rocketmq
===================================

[chinese](https://github.com/AlittleBitch/spring-boot-starter-rocketmq/blob/master/README.md)

spring-boot-starter-rockermq。

支持jdk版本为1.8
SpringBoot版本为1.5.6
maven仓库地址[新aliyun maven](https://maven.aliyun.com/repository/public)

### 如何运行spring-boot-starter-RocketMQ

* 构建项目

```shell
	mvn install
```

* 添加依赖:

```xml
    <dependency>
		<groupId>org.alittlebitch.rocketmq</groupId>
		<artifactId>spring-boot-starter-rocketmq</artifactId>
    	<version>1.0.0</version>
    </dependency>
```

* 添加必要配置application.yml

```yaml
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
	@EnableMQConfiguration
	public class Application {
	    public static void main(String[] args) {
	        SpringApplication.run(Application.class, args);
	    }
	}
```

* 启动Message监听

 实现AbstractMessageListener接口,并指定泛型类型用于指定消费处理器

根据rocketmq内置的

<b>ConsumeOrderlyContext</b>

<b>ConsumeConcurrentlyContext</b>

提供了2种封装

<b>ConsumeContextConcurrently</b>

<b>ConsumeContextOrderly</b>

通过指定不同泛型在consumeMessage方法中对应的context，以及返回值ConsumeStatus有所改变.
如果是ConsumeContextConcurrently返回值因是如下demo示例，如是Orderly则ConsumeStatus.custom().setConsumeOrderlyStatus

```java
	/**
	 * @author ShawnShoper
	 * @date 2018/7/30 11:34
	 */
	@Component
	public class MessageListener extends AbstractMessageListener<ConsumeContextConcurrently> {
	    @Override
	    @ConsumerConfig(topic = "test", tags = "test", consumeFromWhere = ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET)
	    public ConsumeStatus consumeMessage(List<MessageExt> msgs, ConsumeContextConcurrently context) {
	        try {
	            String message = new String(msgs.get(0).getBody(), "UTF-8");
	            System.out.println(new String(message)+System.currentTimeMillis());
	            return ConsumeStatus.custom().setConsumeConcurrentlyStatus(ConsumeConcurrentlyStatus.CONSUME_SUCCESS);
	        } catch (UnsupportedEncodingException e) {
	            System.out.println(".........");
	            e.printStackTrace();
	            return ConsumeStatus.custom().setConsumeConcurrentlyStatus(ConsumeConcurrentlyStatus.RECONSUME_LATER);
	        }
	    }
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