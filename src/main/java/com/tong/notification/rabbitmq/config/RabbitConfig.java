package com.tong.notification.rabbitmq.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RabbitConfig {
    private final RabbitProperties properties;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory=new CachingConnectionFactory();
        // factory config
        cachingConnectionFactory.setUsername("guest");
        cachingConnectionFactory.setPassword("guest");
        cachingConnectionFactory.setHost("127.0.0.1");
        cachingConnectionFactory.setPort(Integer.parseInt("5672"));
        // call back and confirm type
        cachingConnectionFactory.setPublisherReturns(true);
        cachingConnectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        cachingConnectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
        return  cachingConnectionFactory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(connectionFactory());
        containerFactory.setConcurrentConsumers(1);
        containerFactory.setMaxConcurrentConsumers(20);
        containerFactory.setPrefetchCount(1);
        // ack mode
        containerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        // convert from a Message to a Java object
        containerFactory.setMessageConverter(new Jackson2JsonMessageConverter());
        // retry call chain
        containerFactory.setAdviceChain(
                RetryInterceptorBuilder
                        .stateless()
                        .recoverer(new RejectAndDontRequeueRecoverer())
                        .retryOperations(rabbitRetryTemplate())
                        .build()
        );
        return containerFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        // jdk serialization
        // converted to json in the UI
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        // enable callback func
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setRetryTemplate(rabbitRetryTemplate());
        // producer to broker
        rabbitTemplate.setConfirmCallback((correlationData, ack, reason) -> {
            System.out.println("ConfirmCallback：     " + "correlationData：" + correlationData);
            System.out.println("ConfirmCallback：     " + "ack" + ack);
            System.out.println("ConfirmCallback：     " + "reason：" + reason);
        });
        // exchange to queue in the broker
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            System.out.println("ReturnCallback：     " + "message：" + returnedMessage.getMessage());
            System.out.println("ReturnCallback：     " + "replyCode：" + returnedMessage.getReplyCode());
            System.out.println("ReturnCallback：     " + "response：" + returnedMessage.getReplyText());
            System.out.println("ReturnCallback：     " + "exchange：" + returnedMessage.getExchange());
            System.out.println("ReturnCallback：     " + "routing key：" + returnedMessage.getRoutingKey());
        });
        return rabbitTemplate;
    }

    @Bean
    public RetryTemplate rabbitRetryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        // set listener to retry process
        retryTemplate.registerListener(new RetryListener() {
            @Override
            public <T, E extends Throwable> boolean open(RetryContext retryContext, RetryCallback<T, E> retryCallback) {
                // call before execution
                log.info("###retry start");
                return true;
            }

            @Override
            public <T, E extends Throwable> void close(RetryContext retryContext, RetryCallback<T, E> retryCallback, Throwable throwable) {
                // call after retry
                log.info("###last retry");
            }

            @Override
            public <T, E extends Throwable> void onError(RetryContext retryContext, RetryCallback<T, E> retryCallback, Throwable throwable) {
                //  call when any exception
                log.error("the {} time call", retryContext.getRetryCount());
            }
        });
        retryTemplate.setBackOffPolicy(backOffPolicyByProperties());
        retryTemplate.setRetryPolicy(retryPolicyByProperties());
        return retryTemplate;
    }

    @Bean
    public ExponentialBackOffPolicy backOffPolicyByProperties() {
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        long maxInterval = properties.getListener().getSimple().getRetry().getMaxInterval().getSeconds();
        long initialInterval = properties.getListener().getSimple().getRetry().getInitialInterval().getSeconds();
        double multiplier = properties.getListener().getSimple().getRetry().getMultiplier();
        // retry gap
        backOffPolicy.setInitialInterval(initialInterval * 1000);
        // biggest retry gap
        backOffPolicy.setMaxInterval(maxInterval * 1000);
        // multiplier policy for retry
        backOffPolicy.setMultiplier(multiplier);
        return backOffPolicy;
    }

    @Bean
    public SimpleRetryPolicy retryPolicyByProperties() {
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        int maxAttempts = properties.getListener().getSimple().getRetry().getMaxAttempts();
        retryPolicy.setMaxAttempts(maxAttempts);
        return retryPolicy;
    }
}
