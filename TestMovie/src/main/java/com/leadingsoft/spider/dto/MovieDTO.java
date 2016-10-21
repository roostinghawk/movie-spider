package com.leadingsoft.spider.dto;

import com.leadingsoft.bizfuse.common.web.dto.AbstractDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuw on 2016/10/21.
 */
@Getter
@Setter
public class MovieDTO extends AbstractDTO {
    /**
     * 电影名称
     */
    private String name;

    /**
     * 豆瓣电影中的Id
     */
    private Long doubanId;

    /**
     * 电影访问的URL
     */
    private String url;


    /**
     * 导演
     */
    private List<CelebrityDTO> directorList = new ArrayList<>();

    /**
     * 编剧
     */
    private List<CelebrityDTO> writerList = new ArrayList<>();

    /**
     * 主演
     */
    private List<CelebrityDTO> actorList = new ArrayList<>();


    /**
     * 类型
     */
    private String genre;

    /**
     * 上映日期
     */
    private String releaseDate;

    /**
     * IMDB
     */
    private String imdb;

    /**
     * 片长
     */
    private String runTime;

    /**
     * 制片 (暂时不需要此字段)
     */
    private String producer;

    /**
     * 制片国家/地区
     */
    private String zone;

    /**
     * 语言
     */
    private String language;

    /**
     * 又名
     */
    private String otherName;

    /**
     * 评分
     */
    private String rating;

    /**
     * 评价人数
     */
    private String ratingCount;

    /**
     * 五星评比
     */
    private String fiveStarRating;

    /**
     * 四评比
     */
    private String fourStarRating;

    /**
     * 三星评比
     */
    private String threeStarRating;

    /**
     * 二星评比
     */
    private String twoStarRating;

    /**
     * 一星评比
     */
    private String oneStarRating;

    /**
     * 短评数
     */
    private String CommentCount;

    /**
     * 影评数
     */
    private String reviewCount;
}
