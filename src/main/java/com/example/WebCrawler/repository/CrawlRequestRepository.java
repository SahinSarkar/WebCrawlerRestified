package com.example.WebCrawler.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.WebCrawler.domain.CrawlRequest;

@Repository
public interface CrawlRequestRepository extends MongoRepository<CrawlRequest, String> {

}
