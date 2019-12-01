package com.example.WebCrawler.crawlUtils.processor;

import java.util.ArrayList;
import java.util.List;

import com.example.WebCrawler.domain.CrawlRequest;
import com.example.WebCrawler.domain.CrawlerReturnInfo;
import com.example.WebCrawler.domain.PageDetail;
import com.example.WebCrawler.domain.WebCrawlResult;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class CrawlerControllerConfigurer {
	
	public WebCrawlResult configureAndStartCrawling(CrawlRequest crawlRequest, int numberOfCrawlers) throws Exception {
		CrawlController controller;
		String crawlStorageFolder = "/data/crawl/root/crawlRequest" + crawlRequest.getTokenId();

		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setMaxDepthOfCrawling(crawlRequest.getDepth());
		config.setResumableCrawling(true);

		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		controller = new CrawlController(config, pageFetcher, robotstxtServer);
		controller.addSeed(crawlRequest.getUrl());

		CrawlController.WebCrawlerFactory<Crawler> factory = Crawler::new;

		controller.start(factory, numberOfCrawlers);

		WebCrawlResult domain = setDomainInfoFromAllCrawlers(controller);
		domain.setTokenId(crawlRequest.getTokenId());
		return domain;
	}

	public WebCrawlResult setDomainInfoFromAllCrawlers(CrawlController controller) {
		WebCrawlResult domainInfoToReturn = new WebCrawlResult();
		int totalLinks = 0;
		int totalImages = 0;
		List<PageDetail> pageDetailsOfEveryPageTraversed = new ArrayList<>();
		for (Object domainInfo : controller.getCrawlersLocalData()) {
			CrawlerReturnInfo info = (CrawlerReturnInfo) domainInfo;
			totalImages += info.getImagesSeen();
			totalLinks += info.getLinksTraversed();
			
			pageDetailsOfEveryPageTraversed.addAll(info.getPagesDetailList());
		}
		domainInfoToReturn.setTotalImages(totalImages + "");
		domainInfoToReturn.setTotalLinks(totalLinks + "");
		domainInfoToReturn.setDetails(pageDetailsOfEveryPageTraversed);

		return domainInfoToReturn;

	}
}
