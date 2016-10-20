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
     * 制片
     */
    @Length(max = 255)
    @Column(length = 255)
    private String producer;

    /**
     * 语言
     */
    @Length(max = 255)
    @Column(length = 255)
    private String language;

    /**
     * 主演
     */
    @Length(max = 255)
    @Column(length = 255)
    private String actor;

    /**
     * 地区
     */
    @Length(max = 255)
    @Column(length = 255)
    private String zone;

    /**
     * 时长
     */
    @Length(max = 255)
    @Column(length = 255)
    private String duration;

}
