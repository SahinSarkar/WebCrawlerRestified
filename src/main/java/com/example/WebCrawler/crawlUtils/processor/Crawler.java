package com.example.WebCrawler.crawlUtils.processor;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.example.WebCrawler.domain.CrawlerReturnInfo;
import com.example.WebCrawler.domain.PageDetail;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

public class Crawler extends WebCrawler{

    private int linksTraversed = 0;
    private int imagesSeen = 0;
    
	private ArrayList<PageDetail> pageDetails = new ArrayList<>();
    
	CrawlerReturnInfo returnInfo = new CrawlerReturnInfo();
	
	@Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        return true;
    }
	
	private static final Logger logger = LogManager.getLogger(Crawler.class);

    /**
     * This function is called when a page is fetched and ready
     * to be processed by your program.
     */
    @Override
	public void visit(Page page) {

		linksTraversed++;
		PageDetail detailOfThisPage = new PageDetail();
		String url = page.getWebURL().getURL();
		System.out.println("URL: " + url);

		Document document;
		try {
			document = Jsoup.connect(url).get();
			detailOfThisPage.setPageTitle(document.title());
			detailOfThisPage.setPageLink(url);

			Elements imagesOnPage = document.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
			int imageCount = imagesOnPage.size();
			detailOfThisPage.setImageCount(imageCount + "");
			imagesSeen += imageCount;

			pageDetails.add(detailOfThisPage);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
    
    @Override
    public void onBeforeExit() {
    	returnInfo.setTotalLinks(linksTraversed);
    	returnInfo.setTotalImages(imagesSeen);
    	returnInfo.setDetails(pageDetails);
    	logger.info("setting local data for crawler instance = " + getMyId() + ". Local data is = " + returnInfo);
    }

	
	@Override
	public CrawlerReturnInfo getMyLocalData() {
		return returnInfo;
	}
}
