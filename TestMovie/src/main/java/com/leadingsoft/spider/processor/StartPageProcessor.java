package com.leadingsoft.spider.processor;

import com.leadingsoft.spider.pipeline.MovieFilePipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by liuw on 2016/10/6.
 */
public class StartPageProcessor implements PageProcessor {


    private Site site = Site
            .me()
            .setDomain("movie.douban.com");

    @Override
    public void process(Page page) {
        page.putField("urls", page.getHtml().$("#content > div > div.article > a[name=\"类型\"] + table").links().all());
    }

    @Override
    public Site getSite() {
        return site;
    }
}
