package com.example.WebCrawler.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.WebCrawler.domain.WebCrawlResult;

@Repository
public interface CrawlResultsRepository extends MongoRepository<WebCrawlResult, String> {
	
}
