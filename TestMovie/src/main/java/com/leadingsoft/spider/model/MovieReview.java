package com.leadingsoft.spider.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.AbstractPersistable;
import sun.reflect.generics.repository.AbstractRepository;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 电影评价
 *
 * Created by liuw on 2016/10/21.
 */
@Getter
@Setter
@Entity
public class MovieReview extends AbstractPersistable<Long> {

    /**
     * 豆瓣电影中的Id
     */
    private Long doubanId;

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
     * 四评比
     */
    @Length(max = 255)
    @Column(length = 255)
    private String fourStarRating;

    /**
     * 三星评比
     */
    @Length(max = 255)
    @Column(length = 255)
    private String threeStarRating;

    /**
     * 二星评比
     */
    @Length(max = 255)
    @Column(length = 255)
    private String twoStarRating;

    /**
     * 一星评比
     */
    @Length(max = 255)
    @Column(length = 255)
    private String oneStarRating;

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

    /**
     * 导入时间
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * 电影
     */
    @OneToOne
    private Movie movie;
}
