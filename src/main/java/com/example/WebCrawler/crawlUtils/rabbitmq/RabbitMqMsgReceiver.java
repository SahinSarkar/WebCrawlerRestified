package com.example.WebCrawler.crawlUtils.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.example.WebCrawler.crawlUtils.processor.CrawlerControllerConfigurer;
import com.example.WebCrawler.domain.CrawlRequest;
import com.example.WebCrawler.domain.CrawlRequest.Status;
import com.example.WebCrawler.domain.WebCrawlResult;
import com.example.WebCrawler.repository.CrawlRequestRepository;
import com.example.WebCrawler.repository.CrawlResultsRepository;

@RabbitListener(queues = "crawlRequests")
public class RabbitMqMsgReceiver {

	@Value("${number.of.crawlers}")
	private int numberOfParallelCrawlersForEachCrawlRequest;

    private final int instance;

    @Autowired
	private CrawlResultsRepository crawlResultsRepository;
    
    @Autowired
    private CrawlRequestRepository crawlRequestRepository;

    public RabbitMqMsgReceiver(int i) {
        this.instance = i;
    }

    @RabbitHandler
    public void receive(CrawlRequest request) throws InterruptedException {
    	WebCrawlResult crawlResults = null;
    	System.out.println("crawlrequest reached receiver = " + instance + " " + request);
    	try {
    		request.setStatus(Status.INPROCESS);
    		persistChangedRequest(request);
    		crawlResults = new CrawlerControllerConfigurer().configureAndStartCrawling(request, numberOfParallelCrawlersForEachCrawlRequest);
    		request.setStatus(Status.PROCESSED);
    		persistChangedRequest(request);
		} catch (Exception e) {
			request.setStatus(Status.FAILED);
			persistChangedRequest(request);
			e.printStackTrace();
		}
    	
		crawlResultsRepository.save(crawlResults);
    }
    
    private void persistChangedRequest(CrawlRequest request) {
    	crawlRequestRepository.save(request);
    }
}