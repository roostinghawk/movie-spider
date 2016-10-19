package com.leadingsoft.spider;

import com.leadingsoft.spider.pipeline.MovieFilePipeline;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.DuplicateRemovedScheduler;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.component.BloomFilterDuplicateRemover;

/**
 * Created by liuw on 2016/10/4.
 */
public class TestMovie implements PageProcessor {

    public static final String URL_START = "https://movie.douban.com/tag";

    public static final String URL_LIST = "https://movie\\.douban\\.com/tag/*";

    public static final String URL_POST = "https://movie\\.douban\\.com/subject/\\w+";

    private Site site = Site
            .me()
            .setDomain("movie.douban.com")
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.71 Safari/537.36");

    @Override
    public void process(Page page) {
        // 开始页
        if(page.getUrl().toString().equals(URL_START)) {
            // 添加所有类型
            page.addTargetRequests(page.getHtml().$("#content > div > div.article > a[name=\"类型\"] + table").links().all());
        }
        //列表页
        else if (page.getUrl().regex(URL_LIST).match()) {
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
                //.addUrl(URL_START)
                //.addUrl("https://movie.douban.com/tag/%E7%A7%91%E5%B9%BB")
                //.addUrl("https://movie.douban.com/tag/%E7%A7%91%E5%B9%BB?start=1040&type=T")
                .addUrl("https://movie.douban.com/subject/26683290/")
                .addPipeline(new MovieFilePipeline(Consts.ROOT_PATH + "dd"))
                        // 去掉重复的Url
                //.setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000000)))
                .run();

    }

    private void putItemsToPage(Page page){

        page.putField("片名", page.getHtml().$("div#content h1 span:first-child", "innerHtml").all());
        page.putField("导演", page.getHtml().$("div#content div#info span.pl:containsOwn(导演) + span a", "innerHtml").all());
        page.putField("编剧", page.getHtml().$("div#content div#info span.pl:containsOwn(编剧) + span a", "innerHtml").all());
        page.putField("主演", page.getHtml().$("div#content div#info span.actor a[rel=\"v:starring\"]", "innerHtml").all());
        page.putField("类型", page.getHtml().$("div#content div#info span.pl:containsOwn(类型) ~ span[property=\"v:genre\"", "innerHtml").all());
        page.putField("上映日期", page.getHtml().$("div#content div#info span.pl:containsOwn(上映日期) ~ span[property=\"v:initialReleaseDate\"]", "innerHtml").all());

        page.putField("Url", page.getUrl().toString());
        page.putField("制片国家/地区", page.getHtml().regex("制片国家/地区:</span>.*?<br>").toString()
                                                   .replace("制片国家/地区:</span>", "")
                                                   .replace("<br>", "").replace("\n", "").trim());
        page.putField("语言", page.getHtml().regex("语言:</span>.*?<br>").toString()
                .replace("语言:</span>", "").replace("<br>", "").replace("\n", "").trim());

        page.putField("片长", page.getHtml().$("div#content div#info span.pl:containsOwn(片长) + span[property=\"v:runtime\"]", "innerHtml").all());

        page.putField("又名", page.getHtml().regex("又名:</span>.*?<br>").toString()
                .replace("又名:</span>", "").replace("<br>", "").replace("\n", "").trim());

        page.putField("IMDB 连接", page.getHtml().$("div#content div#info span.pl:containsOwn(IMDb链接) + a", "innerHtml").all());

        // 评分
        page.putField("豆瓣评分", page.getHtml().$("div#interest_sectl strong.rating_num", "innerHtml").all());
        // 评价人数
        page.putField("评价人数", page.getHtml().$("div#interest_sectl span[property=\"v:votes\"]", "innerHtml").all());
        // 五星占比
        page.putField("五星占比", page.getHtml().$("#interest_sectl > div.rating_wrap.clearbox > span:nth-of-type(2)", "innerHtml").all());
        // 短评数
        page.putField("短评数", page.getHtml().$("#comments-section > div.mod-hd > h2 > span > a", "innerHtml").get()
                .replace("全部", "").replace("条", ""));
        // 影评数
        page.putField("影评数", page.getHtml().$("#review_section > div.mod-hd > h2 > span > a", "innerHtml").get().replace("全部", ""));
    }
}
