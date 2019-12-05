package com.example.WebCrawler.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "singleCrawlRequestData")
public class WebCrawlResult {

	public WebCrawlResult() {
	}
	
	@Id
	private String tokenId;

	private String totalLinks;
	private String totalImages;
	private List<PageDetail> details;
	
	public WebCrawlResult(String totalLinks, String totalImages, List<PageDetail> details) {
		this.totalLinks = totalLinks;
		this.totalImages = totalImages;
		this.details = details;
	}

	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String id) {
		this.tokenId = id;
	}
	public String getTotalLinks() {
		return totalLinks;
	}
	public void setTotalLinks(String totalLinks) {
		this.totalLinks = totalLinks;
	}
	public String getTotalImages() {
		return totalImages;
	}
	public void setTotalImages(String totalImages) {
		this.totalImages = totalImages;
	}
	public List<PageDetail> getDetails() {
		return details;
	}
	public void setDetails(List<PageDetail> details) {
		this.details = details;
	}
	@Override
	public String toString() {
		return "CrawlResults [id=" + tokenId + ", totalLinks=" + totalLinks + ", totalImages=" + totalImages + ", details="
				+ details + "]";
	}
}
