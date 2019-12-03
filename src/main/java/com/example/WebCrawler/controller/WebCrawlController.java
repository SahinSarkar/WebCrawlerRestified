package com.example.WebCrawler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.WebCrawler.domain.WebCrawlResult;
import com.example.WebCrawler.service.WebCrawlService;

@RestController
public class WebCrawlController {

	@Autowired
	private WebCrawlService service;

	@RequestMapping("crawlAndGetToken")
	public String crawlAndGetToken(@RequestParam String url, @RequestParam int depth) {
		String requestToken = service.returnAcknowledgementToken(url, depth);
		return requestToken;
	}

	@RequestMapping("crawlResult")
	public WebCrawlResult getCrawlResult(@RequestParam String tokenId) {
		WebCrawlResult crawlResult = service.getCrawlResults(tokenId);
		return crawlResult;
	}

	@RequestMapping("requestStatus")
	public String showRequestStatus(@RequestParam String tokenId) {
		String requestStatus = service.getRequestStatus(tokenId);
		return requestStatus;
	}

}
