package com.example.WebCrawler.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.WebCrawler.crawlUtils.rabbitmq.RabbitMqMsgProducer;
import com.example.WebCrawler.domain.CrawlRequest;
import com.example.WebCrawler.domain.CrawlRequest.Status;
import com.example.WebCrawler.domain.CrawlerReturnInfo;
import com.example.WebCrawler.repository.CrawlRequestRepository;
import com.example.WebCrawler.repository.CrawlResultsRepository;

@Service
public class WebCrawlService {

	@Autowired
	private RabbitMqMsgProducer rabbitMqMsgProducer;
	
	@Autowired
	private CrawlRequestRepository crawlRequestRepository;
	
	@Autowired
	private CrawlResultsRepository crawlResultsRepository;
	
	private Logger logger = LogManager.getLogger(WebCrawlService.class);

	public String returnAcknowledgementToken(String URL, int depth) {
		String requestToken;
		CrawlRequest request = new CrawlRequest(URL, depth);
		request.setStatus(Status.SUBMITTED);
		requestToken = request.getTokenId();
		logger.info("persisting request to mongoDB. Request is = " + request);
		crawlRequestRepository.save(request);
		
		logger.info("sending the request to the work queue Queuename::crawlRequests to be processed by consumers later");
		rabbitMqMsgProducer.send(request);
		logger.info("request sent to consumer for later processing = " + request);
		return requestToken;
	}
	
	public CrawlerReturnInfo getCrawlResults(String tokenId) {
		Optional<CrawlerReturnInfo> result = crawlResultsRepository.findById(tokenId);
		logger.info("obtained crawlResult for tokenId = " + tokenId);
		logger.info("crawlResult obtained is = " + result.get());
		return result.get();
	}

	public String getRequestStatus(String tokenId) {
		Optional<CrawlRequest> requestOpt = crawlRequestRepository.findById(tokenId);
		logger.info("obtained crawlRequest for tokenId = " + tokenId);
		CrawlRequest request = requestOpt.get();
		logger.info("crawlRequest obtained is = " + request);
		return request.getStatus().getDisplayString();
	}

}
