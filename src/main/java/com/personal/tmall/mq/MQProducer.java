package com.personal.tmall.mq;

import com.personal.tmall.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
@Slf4j
@Service
public class MQProducer {

    @Autowired
    AmqpTemplate rabbitTemplate;

    @Value("${tmall.rabbitmq.direct.queue}")
    String directQueue;

    @Value("${tmall.rabbitmq.topic.exchange}")
    private String topicExchange;

    @Value("${tmall.rabbitmq.topic.routingkey}")
    private String topicRoutingkey;

    public void produceTopicMsg(UserAndGoodsMsg userAndGoodsMsg) {
        String msg = RedisService.beanToString(userAndGoodsMsg);
        log.info("Sending Message:{}", msg);
        // topic模式
        rabbitTemplate.convertAndSend(topicExchange, topicRoutingkey, msg);
    }

    public void produceDirectMsg(String msg) {
        log.info("Sending Direct Message:{}", msg);
        // Direct模式
        rabbitTemplate.convertAndSend(directQueue, msg);
    }
}
