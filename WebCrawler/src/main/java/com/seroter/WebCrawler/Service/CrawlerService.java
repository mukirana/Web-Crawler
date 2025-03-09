package com.seroter.WebCrawler.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CrawlerService {
    private final ExecutorService executor;
    private final Set<String> visitedUrls = Collections.synchronizedSet(new HashSet<>());

    public CrawlerService(int threads) {
        this.executor = Executors.newFixedThreadPool(threads);
    }

    public void startCrawling(List<String> domains) {
        for (String domain : domains) {
            executor.submit(() -> new WebCrawler(domain, visitedUrls).crawl(domain));
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
