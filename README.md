spring-boot-starter-rocketmq
===================================

[chinese](https://github.com/AlittleBitch/spring-boot-starter-rocketmq/blob/master/README.md)

spring-boot-starter-rockermq。

支持jdk版本为1.8

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

* 启动Message监听

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

Demo可参考test包下，稍后会继续补充使用文档。