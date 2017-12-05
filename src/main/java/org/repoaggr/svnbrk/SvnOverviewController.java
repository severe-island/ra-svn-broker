package org.repoaggr.svnbrk;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class SvnOverviewController {
    @RequestMapping("/overview")
    public SvnOverview overview(@RequestParam(value="url", defaultValue="localhost") String url) {
        // Здесь будет сбор информации с репозитория
        return new SvnOverview(
            0, url, 0
        );
    }
}