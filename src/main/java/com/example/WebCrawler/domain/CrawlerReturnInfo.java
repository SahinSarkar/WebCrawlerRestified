package com.example.WebCrawler.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "singleCrawlRequestData")
public class CrawlerReturnInfo {

	@Id
	private String tokenId;
	private int totalLinks;
	private int totalImages;
	private List<PageDetail> details;

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public int getTotalLinks() {
		return totalLinks;
	}

	public void setTotalLinks(int totalLinks) {
		this.totalLinks = totalLinks;
	}

	public int getTotalImages() {
		return totalImages;
	}

	public void setTotalImages(int totalImages) {
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
		return "CrawlerReturnInfo [tokenId=" + tokenId + ", totalLinks=" + totalLinks + ", totalImages=" + totalImages
				+ ", details=" + details + "]";
	}

}
