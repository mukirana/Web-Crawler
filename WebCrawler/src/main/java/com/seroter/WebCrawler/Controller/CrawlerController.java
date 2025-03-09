package com.seroter.WebCrawler.Controller;

import com.seroter.WebCrawler.Service.CrawlerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/crawl")
public class CrawlerController {

    @GetMapping(path="/start")
    public void startCrawl(){
        List<String> domains = Arrays.asList(
                "https://www.flipkart.com/",
                "https://amazon.com/"
        );

        CrawlerService controller = new CrawlerService(5); // 5 threads
        controller.startCrawling(domains);
    }
}
