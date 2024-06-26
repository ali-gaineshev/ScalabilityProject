package seng468scalability.com.stock_transactions.util;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import seng468scalability.com.stock_transactions.entity.StockTransaction;
import seng468scalability.com.stock_transactions.request.CancelOrderRequest;

@Service
@RequiredArgsConstructor
public class RabbitMQProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing_key.name}")
    private String routingKey;


    @Value("${rabbitmq.cancelorder.routing_key.name}")
    private String cancelOrderRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

    public void sendMessage(StockTransaction message){
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        LOGGER.info(String.format("Message sent -> %s", message.toString()));
    }

    public void cancelOrderMessage(CancelOrderRequest message){
        rabbitTemplate.convertAndSend(exchange, cancelOrderRoutingKey, message);
        LOGGER.info(String.format("Message sent to cancel order -> %s", message.toString()));
    }
}
