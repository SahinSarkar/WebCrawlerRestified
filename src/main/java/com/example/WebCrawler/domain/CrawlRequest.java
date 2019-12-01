package com.example.WebCrawler.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "requestMetadata")
public class CrawlRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String tokenId;
	private String url;
	private int depth;

	public enum Status {
		SUBMITTED, INPROCESS, PROCESSED, FAILED
	};

	private Status status;

	public CrawlRequest(String url, int depth) {
		super();
		this.url = url;
		this.depth = depth;
		this.tokenId = Long.toString(System.currentTimeMillis());
	}

	public CrawlRequest() {
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String getTokenId() {
		return tokenId;
	}

	void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CrawlRequest [tokenId=" + tokenId + ", url=" + url + ", depth=" + depth + ", status=" + status + "]";
	}

}
