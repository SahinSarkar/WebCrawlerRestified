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

	public long returnAcknowledgementToken(String URL, int depth) {
		long requestToken;
		CrawlRequest request = new CrawlRequest(URL, depth);
		request.setStatus(Status.SUBMITTED);
		requestToken = Long.parseLong(request.getTokenId());
		crawlRequestRepository.save(request);
		rabbitMqMsgProducer.send(request);
		return requestToken;
	}
	
	public WebCrawlResult getCrawlResults(long tokenId) {
		Optional<WebCrawlResult> result = crawlResultsRepository.findById(Long.toString(tokenId));
		return result.get();
	}

	public String getRequestStatus(long tokenId) {
		Optional<CrawlRequest> requestOpt = crawlRequestRepository.findById(Long.toString(tokenId));
		CrawlRequest request = requestOpt.get();
		return request.getStatus().toString();
	}

}
