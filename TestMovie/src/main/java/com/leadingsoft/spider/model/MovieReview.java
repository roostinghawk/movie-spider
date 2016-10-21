package com.leadingsoft.spider.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.AbstractPersistable;
import sun.reflect.generics.repository.AbstractRepository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

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
