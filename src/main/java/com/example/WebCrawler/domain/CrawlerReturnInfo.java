package com.example.WebCrawler.domain;

import java.util.List;

public class CrawlerReturnInfo {

	private int linksTraversed;
	private int imagesSeen;
	private List<PageDetail> pagesDetailList;

	public CrawlerReturnInfo(int linksTraversed, int imagesSeen, List<PageDetail> pagesDetailList) {
		super();
		this.linksTraversed = linksTraversed;
		this.imagesSeen = imagesSeen;
		this.pagesDetailList = pagesDetailList;
	}

	public int getLinksTraversed() {
		return linksTraversed;
	}

	public void setLinksTraversed(int linksTraversed) {
		this.linksTraversed = linksTraversed;
	}

	public int getImagesSeen() {
		return imagesSeen;
	}

	public CrawlerReturnInfo() {
	}

	public void setImagesSeen(int imagesSeen) {
		this.imagesSeen = imagesSeen;
	}

	public List<PageDetail> getPagesDetailList() {
		return pagesDetailList;
	}

	public void setPagesDetailList(List<PageDetail> pagesDetailList) {
		this.pagesDetailList = pagesDetailList;
	}

}
