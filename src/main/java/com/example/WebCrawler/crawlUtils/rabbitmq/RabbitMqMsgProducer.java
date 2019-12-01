package com.example.WebCrawler.crawlUtils.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.WebCrawler.domain.CrawlRequest;

public class RabbitMqMsgProducer {
	
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;
    
    public void send(CrawlRequest crawlRequest) {
    	System.out.println("added crawlrequest to queue = " + crawlRequest);
		template.convertAndSend(queue.getName(), crawlRequest);
    }
}