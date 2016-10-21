package com.leadingsoft.spider.processor.douban;

import com.leadingsoft.spider.dto.CelebrityDTO;
import com.leadingsoft.spider.dto.MovieDTO;
import com.leadingsoft.spider.model.Movie;
import com.leadingsoft.spider.pipeline.MovieFilePipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuw on 2016/10/6.
 */
public class DoubanMovieProcessor implements PageProcessor {

    public static final String URL_LIST = "https://movie\\.douban\\.com/tag/*";

    public static final String URL_POST = "https://movie\\.douban\\.com/subject/\\w+";

    public static final String URL_ID_PATTEN = "https://movie\\.douban\\.com/subject/([0-9]+)/";

    public static final String URL_CELEBRITY_PATTEN = "https://movie\\.douban\\.com/celebrity/([0-9]+)/";

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

        MovieDTO dto = new MovieDTO();

        // 片名
        String name = html.$("div#content h1 span:first-child", "innerHtml").get();
        // 片名是必要条件
        if(name == null) {
            return;
        }
        dto.setName(name);
        // Url
        dto.setUrl(page.getUrl().toString());
        // 豆瓣Id
        dto.setDoubanId(this.getMovieIdFromUrl(dto.getUrl()));
        // 导演
        dto.setDirectorList(this.getCelebrityList(html, "div#content div#info span.pl:containsOwn(导演) + span a"));
        // 编剧
        dto.setWriterList(this.getCelebrityList(html, "div#content div#info span.pl:containsOwn(编剧) + span a"));
        // 主演
        dto.setActorList(this.getCelebrityList(html, "div#content div#info span.actor a[rel=\"v:starring\"]"));
        // 类型
        List<String> genreList = html.$("div#content div#info span.pl:containsOwn(类型) ~ span[property=\"v:genre\"", "innerHtml").all();
        if(genreList != null && genreList.size() > 0) {
            dto.setGenre(Arrays.toString(genreList.toArray()));
        }
        // 上映日期
        List<String> releaseDateList = html.$("div#content div#info span.pl:containsOwn(上映日期) ~ span[property=\"v:initialReleaseDate\"]", "innerHtml").all();
        if(releaseDateList != null && releaseDateList.size() > 0) {
            dto.setReleaseDate(Arrays.toString(releaseDateList.toArray()));
        }
        // IMDB
        dto.setImdb(html.$("div#content div#info span.pl:containsOwn(IMDb链接) + a", "innerHtml").get());
        // 片长
        List<String> runTimeList = html.$("div#content div#info span.pl:containsOwn(片长) + span[property=\"v:runtime\"]", "innerHtml").all();
        if(runTimeList != null && runTimeList.size() > 0) {
            dto.setRunTime(Arrays.toString(runTimeList.toArray()));
        }
        // 制片国家/地区
        Selectable zoneRegexResult =  html.regex("制片国家/地区:</span>.*?<br>");
        if(zoneRegexResult != null && zoneRegexResult.toString() != null) {
            dto.setZone(zoneRegexResult.toString()
                    .replace("制片国家/地区:</span>", "")
                    .replace("<br>", "").replace("\n", "").trim());
        }
        // 语言
        Selectable langRegexResult = html.regex("语言:</span>.*?<br>");
        if(langRegexResult != null && langRegexResult.toString() != null){
            dto.setLanguage(langRegexResult.toString()
                    .replace("语言:</span>", "").replace("<br>", "").replace("\n", "").trim());
        }
        // 又名
        Selectable otherNameRegexResult = html.regex("又名:</span>.*?<br>");
        if(otherNameRegexResult != null && otherNameRegexResult.toString() != null) {
            dto.setOtherName(otherNameRegexResult.toString()
                    .replace("又名:</span>", "").replace("<br>", "").replace("\n", "").trim());
        }
        // 评分
        dto.setRating(html.$("div#interest_sectl strong.rating_num", "innerHtml").get());
        // 评价人数
        dto.setRatingCount(html.$("div#interest_sectl span[property=\"v:votes\"]", "innerHtml").get());
        // 五星占比
        dto.setFiveStarRating(html.$("#interest_sectl > div.rating_wrap.clearbox > span:nth-of-type(2)", "innerHtml").get());
        // 四星占比
        dto.setFourStarRating(html.$("#interest_sectl > div.rating_wrap.clearbox > span:nth-of-type(4)", "innerHtml").get());
        // 三星占比
        dto.setThreeStarRating(html.$("#interest_sectl > div.rating_wrap.clearbox > span:nth-of-type(6)", "innerHtml").get());
        // 二星占比
        dto.setTwoStarRating(html.$("#interest_sectl > div.rating_wrap.clearbox > span:nth-of-type(8)", "innerHtml").get());
        // 一星占比
        dto.setOneStarRating(html.$("#interest_sectl > div.rating_wrap.clearbox > span:nth-of-type(10)", "innerHtml").get());
        // 短评数
        String commentCountStr = html.$("#comments-section > div.mod-hd > h2 > span > a", "innerHtml").get();
        if(commentCountStr != null) {
            dto.setCommentCount(commentCountStr.replace("全部", "").replace("条", ""));
        }
        // 影评数
        String reviewCountStr = html.$("#review_section > div.mod-hd > h2 > span > a", "innerHtml").get();
        if(reviewCountStr != null) {
            dto.setReviewCount(reviewCountStr.replace("全部", ""));
        }

        page.putField("movie", dto);
    }


    /**
     * 从URL中取得电影Id
     * @param url
     * @return
     */
    private Long getMovieIdFromUrl(String url) {
        Matcher matcher= Pattern.compile(URL_ID_PATTEN).matcher(url);
        if(matcher.matches()){
            return Long.parseLong(matcher.group(1));
        } else {
            return null;
        }
    }

    /**
     * 从页面取得导演信息列表
     * @param html
     * @param cssSelector
     * @return
     */
    private List<CelebrityDTO> getCelebrityList(Html html, String cssSelector){
        List<CelebrityDTO> celebrityList = new ArrayList<CelebrityDTO>();

        List<String> directorLinks = html.$(cssSelector).links().all();
        List<String> directorNameList = html.$(cssSelector, "innerHtml").all();
        if(directorLinks != null && directorLinks.size() > 0) {
            for (int i = 0; i < directorLinks.size(); i++) {
                CelebrityDTO celebrityDTO = new CelebrityDTO();
                celebrityDTO.setName(directorNameList.get(i));
                celebrityDTO.setUrl(directorLinks.get(i));
                if(celebrityDTO.getUrl() != null) {
                    Matcher matcher = Pattern.compile(URL_CELEBRITY_PATTEN).matcher(celebrityDTO.getUrl());
                    // 如果不匹配，就不保存此数据
                    if(!matcher.matches()){
                        continue;
                    }
                    celebrityDTO.setDoubanId(Long.parseLong(matcher.group(1))); // 如果有url，就应该有匹配的Id
                } else {
                    continue;
                }
                celebrityList.add(celebrityDTO);
            }
        }

        return celebrityList;
    }
}
