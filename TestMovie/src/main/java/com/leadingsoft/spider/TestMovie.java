package com.leadingsoft.spider;

import com.leadingsoft.spider.pipeline.MovieFilePipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by liuw on 2016/10/4.
 */
public class TestMovie implements PageProcessor {

    public static final String URL_LIST = "https://movie\\.douban\\.com/tag/*";

    public static final String URL_POST = "https://movie\\.douban\\.com/subject/\\w+";

    private Site site = Site
            .me()
            .setDomain("movie.douban.com");

    @Override
    public void process(Page page) {
        //列表页
        if (page.getUrl().regex(URL_LIST).match()) {
            // 添加详细页
            page.addTargetRequests(page.getHtml().xpath("//div[@class=\"pl2\"]").links().regex(URL_POST).all());
            // 添加下一页的URL
            page.addTargetRequests(page.getHtml().xpath("//span[@class=\"next\"]/a").links().all());
        //详情页
        } else {
            this.putItemsToPage(page);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new TestMovie())
                .addUrl("https://movie.douban.com/tag/%E7%A7%91%E5%B9%BB")
                //.addUrl("https://movie.douban.com/subject/25786060/")
                .addPipeline(new MovieFilePipeline())
                .run();
    }

    private void putItemsToPage(Page page){

        page.putField("片名", page.getHtml().$("div#content h1 span:first-child", "innerHtml").all());
        page.putField("导演", page.getHtml().$("div#content div#info span.pl:containsOwn(导演) + span a", "innerHtml").all());
        page.putField("编剧", page.getHtml().$("div#content div#info span.pl:containsOwn(编剧) + span a", "innerHtml").all());
        page.putField("主演", page.getHtml().$("div#content div#info span.actor a[rel=\"v:starring\"]", "innerHtml").all());
        page.putField("类型", page.getHtml().$("div#content div#info span.pl:containsOwn(类型) ~ span[property=\"v:genre\"", "innerHtml").all());
        page.putField("上映日期", page.getHtml().$("div#content div#info span.pl:containsOwn(上映日期) ~ span[property=\"v:initialReleaseDate\"]", "innerHtml").all());
        page.putField("片长", page.getHtml().$("div#content div#info span.pl:containsOwn(片长) + span[property=\"v:runtime\"]", "innerHtml").all());
        page.putField("IMDB 连接", page.getHtml().$("div#content div#info span.pl:containsOwn(IMDb链接) + a", "innerHtml").all());
        page.putField("Url", page.getUrl().toString());
        // TODO: 地区语言
    }
}
