package com.example.WebCrawler.crawlUtils.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

	@Bean
	public Queue crawlRequests() {
		return new Queue("crawlRequests");
	}

	// creating two instances of RabbitMQ Receivers to improve latency 
	@Bean
	public RabbitMqMsgReceiver receiver1() {
		return new RabbitMqMsgReceiver(1);
	}

	@Bean
	public RabbitMqMsgReceiver receiver2() {
		return new RabbitMqMsgReceiver(2);
	}

	@Bean
	public RabbitMqMsgProducer sender() {
		return new RabbitMqMsgProducer();
	}
}