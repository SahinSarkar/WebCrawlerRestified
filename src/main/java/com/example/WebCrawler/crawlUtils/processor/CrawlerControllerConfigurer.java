package com.example.WebCrawler.crawlUtils.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	
	private static final Logger logger = LogManager.getLogger(CrawlerControllerConfigurer.class);

	public WebCrawlResult configureAndStartCrawling(CrawlRequest crawlRequest, int numberOfCrawlers) throws Exception {
		logger.info("starting to configure Crawler controller for request = " + crawlRequest);
		CrawlController controller;
		String crawlStorageFolder = "/data/crawl/root/crawlRequest" + crawlRequest.getTokenId();

		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setMaxDepthOfCrawling(crawlRequest.getDepth());
		config.setResumableCrawling(true);
		logger.info("configured crawlerConfig as = " + config);

		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		controller = new CrawlController(config, pageFetcher, robotstxtServer);
		controller.addSeed(crawlRequest.getUrl());

		CrawlController.WebCrawlerFactory<Crawler> factory = Crawler::new;

		logger.info("starting crawling operation for request = " + crawlRequest 
				+ "\n*******This is a blocking operation.*******");
		controller.start(factory, numberOfCrawlers);

		logger.info("********blocking crawl process ends****** \nfor request = " + crawlRequest);
		WebCrawlResult crawlResult = setDomainInfoFromAllCrawlers(controller);
		crawlResult.setTokenId(crawlRequest.getTokenId());
		logger.info("returning crawl result = " + crawlRequest);
		return crawlResult;
	}

	public WebCrawlResult setDomainInfoFromAllCrawlers(CrawlController controller) {
		logger.info("aggregating local data from all crawlers");
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

		logger.info("collected and aggregated data from all crawlers is = " + domainInfoToReturn);
		return domainInfoToReturn;

	}
}
