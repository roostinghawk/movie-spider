package com.leadingsoft.spider.dto;

import com.leadingsoft.bizfuse.common.web.dto.AbstractDTO;
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
public class CelebrityDTO extends AbstractDTO {

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
    private String name;

    /**
     * 英文名
     */
    private String englishName;
}
