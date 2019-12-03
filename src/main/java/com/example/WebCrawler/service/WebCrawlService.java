package com.example.WebCrawler.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.WebCrawler.crawlUtils.rabbitmq.RabbitMqMsgProducer;
import com.example.WebCrawler.domain.CrawlRequest;
import com.example.WebCrawler.domain.CrawlRequest.Status;
import com.example.WebCrawler.domain.WebCrawlResult;
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

	public String returnAcknowledgementToken(String URL, int depth) {
		String requestToken;
		CrawlRequest request = new CrawlRequest(URL, depth);
		request.setStatus(Status.SUBMITTED);
		requestToken = request.getTokenId();
		crawlRequestRepository.save(request);
		rabbitMqMsgProducer.send(request);
		return requestToken;
	}
	
	public WebCrawlResult getCrawlResults(String tokenId) {
		Optional<WebCrawlResult> result = crawlResultsRepository.findById(tokenId);
		return result.get();
	}

	public String getRequestStatus(String tokenId) {
		Optional<CrawlRequest> requestOpt = crawlRequestRepository.findById(tokenId);
		CrawlRequest request = requestOpt.get();
		return request.getStatus().toString();
	}

}
