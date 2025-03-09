package com.seroter.WebCrawler.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.*;

public class WebCrawler {
    private final String domain;
    private final Set<String> visitedUrls;
    private final Queue<String> urlQueue = new LinkedList<>();
    //private final RobotsHandler robotsHandler;

    private static final List<String> PRODUCT_PATTERNS = Arrays.asList(
            "/product/", "/item/", "/p/", "/shop/"
    );

    public WebCrawler(String domain, Set<String> visitedUrls) {
        this.domain = domain;
        this.visitedUrls = visitedUrls;
        //this.robotsHandler = new RobotsHandler(domain);
        urlQueue.add(domain);
    }

    public void crawl(String startUrl) {
        while (!urlQueue.isEmpty()) {
            String url = urlQueue.poll();
            if (url == null || visitedUrls.contains(url)) continue;

            try {
                Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
                visitedUrls.add(url);

                // Extract all links
                Elements links = doc.select("a[href]");
                for (Element link : links) {
                    String absUrl = link.absUrl("href");
                    if (!visitedUrls.contains(absUrl) && isProductUrl(absUrl)) {
                        System.out.println("Product URL Found: " + absUrl);
                        visitedUrls.add(absUrl);
                    } else if (!visitedUrls.contains(absUrl) && absUrl.startsWith(domain)) {
                        urlQueue.add(absUrl); // Continue crawling
                    }
                }

            } catch (IOException e) {
                System.err.println("Failed to fetch: " + url);
            }
        }
    }

    private boolean isProductUrl(String url) {
        for (String pattern : PRODUCT_PATTERNS) {
            if (url.contains(pattern)) {
                return true;
            }
        }
        return false;
    }
}
