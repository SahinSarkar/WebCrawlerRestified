package com.example.WebCrawler.domain;

public class PageDetail {

	public PageDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	private String pageTitle;
	private String pageLink;
	private String imageCount;
	public PageDetail(String pageTitle, String pageLink, String imageCount) {
		super();
		this.pageTitle = pageTitle;
		this.pageLink = pageLink;
		this.imageCount = imageCount;
	}
	public String getPageTitle() {
		return pageTitle;
	}
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	public String getPageLink() {
		return pageLink;
	}
	public void setPageLink(String pageLink) {
		this.pageLink = pageLink;
	}
	public String getImageCount() {
		return imageCount;
	}
	public void setImageCount(String imageCount) {
		this.imageCount = imageCount;
	}
	@Override
	public String toString() {
		return "PageDetail [pageTitle=" + pageTitle + ", pageLink=" + pageLink + ", imageCount=" + imageCount + "]";
	}
	
	
}
