package com.leadingsoft.spider.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by liuw on 2016/10/21.
 */
@Getter
@Setter
@Entity
public class Celebrity extends AbstractPersistable<Long> {

    /**
     * 豆瓣电影中的影人Id
     */
    private Long doubanId;

    /**
     * 影人URL
     */
    private String url;

    /**
     * 中文名
     */
    @NotBlank
    @Length(max = 255)
    @Column(length = 255)
    private String name;

    /**
     * 英文名
     */
    @Length(max = 255)
    @Column(length = 255)
    private String englishName;
}
