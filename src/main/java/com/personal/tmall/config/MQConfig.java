package com.personal.tmall.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
@Configuration
public class MQConfig {

    /**
     * queue:
     * Queues是Massage的落脚点和等待接收的地方
     */
    @Value("${tmall.rabbitmq.direct.queue}")
    String directQueue;

    @Value("${tmall.rabbitmq.topic.queue}")
    String topicQueue;

    /**
     * exchange:
     * 投递消息到queue都是经由exchanges完成的
     * 每个exchange携带一个消息并且路由到Queues
     */
    @Value("${tmall.rabbitmq.topic.exchange}")
    String topicExchange;

    @Value("${tmall.rabbitmq.topic.routingkey}")
    private String topicRoutingkey;


    /**
     * Direct模式 = directQueue
     */
    @Bean
    public Queue queue() {
        return new Queue(directQueue, true);
    }

    /**
     * Topic模式 = topicQueue+topicExchange+topicBinding
     */
    @Bean
    Queue topicQueue() {
        return new Queue(topicQueue, false);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(topicExchange);
    }

    /**
     * Bindings：消息的路由涉及到一个和exchanges类型及一些规则相关的算法
     * 也就是说消息的路由需要遵守一定的规则，这些规则我们就称之为Bindings。
     *
     * @return binding
     */
    @Bean
    Binding topicBinding() {
        return BindingBuilder
                .bind(topicQueue())
                .to(topicExchange())
                .with(topicRoutingkey);
    }
}
