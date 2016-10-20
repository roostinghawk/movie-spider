package com.leadingsoft.spider.processor.douban;

import com.leadingsoft.spider.model.Movie;
import com.leadingsoft.spider.pipeline.MovieFilePipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liuw on 2016/10/6.
 */
public class DoubanMovieProcessor implements PageProcessor {

    public static final String URL_LIST = "https://movie\\.douban\\.com/tag/*";

    public static final String URL_POST = "https://movie\\.douban\\.com/subject/\\w+";

    private Site site = Site
            .me()
            .setDomain("movie.douban.com");
//            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
//            .addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
//            .setCharset("UTF-8")
//            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0");
            //.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.71 Safari/537.36");;


    @Override
    public void process(Page page) {
        // 开始页
        if(page.getUrl().toString().equals("https://movie.douban.com/tag/")) {
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

    private void putItemsToPage(Page page){
        Html html = page.getHtml();

        // 判读是否为电视剧
        List<String> episodeCountList = html.regex("集数:</span>.*?<br>").all();
        if(episodeCountList != null && episodeCountList.size() > 0) {
            return;
        }

        Movie movie = new Movie();

        // 片名
        movie.setName(html.$("div#content h1 span:first-child", "innerHtml").get());
        // Url
        movie.setUrl(page.getUrl().toString());
        // 导演
        List<String> directorList = html.$("div#content div#info span.pl:containsOwn(导演) + span a", "innerHtml").all();
        if(directorList != null && directorList.size() > 0) {
            movie.setDirector(Arrays.toString(directorList.toArray()));
        }
        // 编剧
        List<String> writerList = html.$("div#content div#info span.pl:containsOwn(编剧) + span a", "innerHtml").all();
        if(writerList != null && writerList.size() > 0) {
            movie.setWriter(Arrays.toString(writerList.toArray()));
        }
        // 主演
        List<String> actorList = html.$("div#content div#info span.actor a[rel=\"v:starring\"]", "innerHtml").all();
        if(actorList != null && actorList.size() > 0) {
            movie.setActor(Arrays.toString(actorList.toArray()));
        }
        // 类型
        List<String> genreList = html.$("div#content div#info span.pl:containsOwn(类型) ~ span[property=\"v:genre\"", "innerHtml").all();
        if(genreList != null && genreList.size() > 0) {
            movie.setGenre(Arrays.toString(genreList.toArray()));
        }
        // 上映日期
        List<String> releaseDateList = html.$("div#content div#info span.pl:containsOwn(上映日期) ~ span[property=\"v:initialReleaseDate\"]", "innerHtml").all();
        if(releaseDateList != null && releaseDateList.size() > 0) {
            movie.setReleaseDate(Arrays.toString(releaseDateList.toArray()));
        }
        // IMDB
        movie.setImdb(html.$("div#content div#info span.pl:containsOwn(IMDb链接) + a", "innerHtml").get());
        // 片长
        List<String> runTimeList = html.$("div#content div#info span.pl:containsOwn(片长) + span[property=\"v:runtime\"]", "innerHtml").all();
        if(runTimeList != null && runTimeList.size() > 0) {
            movie.setRunTime(Arrays.toString(runTimeList.toArray()));
        }
        // 制片国家/地区
        movie.setZone(html.regex("制片国家/地区:</span>.*?<br>").toString()
                .replace("制片国家/地区:</span>", "")
                .replace("<br>", "").replace("\n", "").trim());
        // 语言
        movie.setLanguage(html.regex("语言:</span>.*?<br>").toString()
                .replace("语言:</span>", "").replace("<br>", "").replace("\n", "").trim());
        // 又名
        movie.setOtherName(html.regex("又名:</span>.*?<br>").toString()
                .replace("又名:</span>", "").replace("<br>", "").replace("\n", "").trim());
        // 评分
        movie.setRating(html.$("div#interest_sectl strong.rating_num", "innerHtml").get());
        // 评价人数
        movie.setRatingCount(html.$("div#interest_sectl span[property=\"v:votes\"]", "innerHtml").get());
        // 五星占比
        movie.setFiveStarRating(html.$("#interest_sectl > div.rating_wrap.clearbox > span:nth-of-type(2)", "innerHtml").get());
        // 短评数
        movie.setCommentCount(html.$("#comments-section > div.mod-hd > h2 > span > a", "innerHtml").get()
                .replace("全部", "").replace("条", ""));
        // 影评数
        movie.setReviewCount(html.$("#review_section > div.mod-hd > h2 > span > a", "innerHtml").get().replace("全部", ""));

        page.putField("movie", movie);
    }
}
