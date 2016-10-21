package com.leadingsoft.spider.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by liuw on 2016/9/30.
 */
@Getter
@Setter
@Entity
public class Movie extends AbstractPersistable<Long> {


    /**
     * 豆瓣电影中的Id
     */
    private Long doubanId;

    /**
     * 电影名称
     */
    @NotBlank
    @Length(max = 255)
    @Column(length = 255)
    private String name;


    /**
     * 电影访问的URL
     */
    @NotBlank
    @Length(max = 1000)
    @Column(length = 1000)
    private String url;

    /**
     * 导演
     */
    @Length(max = 255)
    @Column(length = 255)
    private String director;

    /**
     * 编剧
     */
    @Length(max = 255)
    @Column(length = 255)
    private String writer;

    /**
     * 主演
     */
    @Length(max = 255)
    @Column(length = 255)
    private String actor;


    /**
     * 类型
     */
    @Length(max = 255)
    @Column(length = 255)
    private String genre;

    /**
     * 上映日期
     */
    @Length(max = 255)
    @Column(length = 255)
    private String releaseDate;

    /**
     * IMDB
     */
    @Length(max = 255)
    @Column(length = 255)
    private String imdb;

    /**
     * 片长
     */
    @Length(max = 255)
    @Column(length = 255)
    private String runTime;

    /**
     * 制片
     */
    @Length(max = 255)
    @Column(length = 255)
    private String producer;

    /**
     * 制片国家/地区
     */
    @Length(max = 255)
    @Column(length = 255)
    private String zone;

    /**
     * 语言
     */
    @Length(max = 255)
    @Column(length = 255)
    private String language;

    /**
     * 又名
     */
    @Length(max = 255)
    @Column(length = 255)
    private String otherName;

    /**
     * 评分
     */
    @Length(max = 255)
    @Column(length = 255)
    private String rating;

    /**
     * 评价人数
     */
    @Length(max = 255)
    @Column(length = 255)
    private String ratingCount;

    /**
     * 五星评比
     */
    @Length(max = 255)
    @Column(length = 255)
    private String fiveStarRating;

    /**
     * 短评数
     */
    @Length(max = 255)
    @Column(length = 255)
    private String CommentCount;

    /**
     * 影评数
     */
    @Length(max = 255)
    @Column(length = 255)
    private String reviewCount;

}
