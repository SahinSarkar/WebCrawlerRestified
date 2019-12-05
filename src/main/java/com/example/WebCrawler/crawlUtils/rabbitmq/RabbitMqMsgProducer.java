package com.example.WebCrawler.crawlUtils.rabbitmq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.WebCrawler.domain.CrawlRequest;

public class RabbitMqMsgProducer {
	
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;
    
    private Logger logger = LogManager.getLogger(RabbitMqMsgProducer.class);
    
    public void send(CrawlRequest crawlRequest) {
    	logger.info("adding crawlrequest to queue = " + crawlRequest);
		template.convertAndSend(queue.getName(), crawlRequest);
		logger.info("added crawlrequest to queue = " + crawlRequest);
    }
}