package com.example.WebCrawler.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.WebCrawler.domain.CrawlerReturnInfo;
import com.example.WebCrawler.service.WebCrawlService;

@RestController
public class WebCrawlController {

	@Autowired
	private WebCrawlService service;
	
	private Logger logger = LogManager.getLogger(WebCrawlController.class);

	@RequestMapping("crawlAndGetToken")
	public String crawlAndGetToken(@RequestParam String url, @RequestParam int depth) {
		logger.info("got request GET on path /crawlAndGetToken. Request fields are = url:" + url + ", depth:" + depth);
		String requestToken = service.returnAcknowledgementToken(url, depth);
		logger.info("token being sent for the accepted request is = " + requestToken);
		return requestToken;
	}

	@RequestMapping("crawlResult")
	public CrawlerReturnInfo getCrawlResult(@RequestParam String tokenId) {
		logger.info("got request GET on path /crawlResult. Request fields are = tokenId:" + tokenId);
		CrawlerReturnInfo crawlResult = service.getCrawlResults(tokenId);
		logger.info("crawl result being sent for the accepted request is = " + crawlResult);
		return crawlResult;
	}

	@RequestMapping("requestStatus")
	public String showRequestStatus(@RequestParam String tokenId) {
		logger.info("got request GET on path /requestStatus. Request fields are = tokenId:" + tokenId);
		String requestStatus = service.getRequestStatus(tokenId);
		logger.info("request status being sent for the accepted API request is = " + requestStatus);
		return requestStatus;
	}

}
