package com.example.WebCrawler.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "requestMetadata")
public class CrawlRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final int LENGTH_OF_RANDOM_TOKEN = 20;

	@Id
	private String tokenId;
	private String url;
	private int depth;

	private String status;

	public CrawlRequest(String url, int depth) {
		this.url = url;
		this.depth = depth;
		this.tokenId = makeTokenId();
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
		return Status.findByDisplayString(status);
	}

	public void setStatus(Status status) {
		this.status = status.getDisplayString();
	}

	@Override
	public String toString() {
		return "CrawlRequest [tokenId=" + tokenId + ", url=" + url + ", depth=" + depth + ", status=" + status + "]";
	}

	private String makeTokenId() {

		UniformRandomProvider rng = RandomSource.create(RandomSource.MT);
		RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('a', 'z')
				.usingRandom(rng::nextInt).build();
		String tokenId = generator.generate(LENGTH_OF_RANDOM_TOKEN);
		return tokenId;
	}

	public enum Status {
		SUBMITTED("Submitted"), INPROCESS("In-process"), PROCESSED("Processed"), FAILED("Failed");
		
		private final String displayString;
		
		private Status(String displayString){
			this.displayString = displayString;
		}
		
		public String getDisplayString() {
			return this.displayString;
		}
		
		private static final Map<String, Status> statusMap;
		
		static {
			statusMap = new HashMap<>();
			for(Status status : Status.values()) {
				statusMap.put(status.displayString, status);
			}
		}
		
		public static Status findByDisplayString(String dispStr) {
			return statusMap.get(dispStr);
		}
	};
}
