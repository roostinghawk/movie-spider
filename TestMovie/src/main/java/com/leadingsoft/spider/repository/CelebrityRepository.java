package com.leadingsoft.spider.repository;


import com.leadingsoft.spider.model.Celebrity;
import com.leadingsoft.spider.model.Movie;
import org.springframework.data.repository.Repository;

/**
 * Created by liuw on 2016/9/30.
 */
public interface CelebrityRepository extends Repository<Celebrity, Long> {

    Celebrity findOne(Long id);

    Celebrity findOneByDoubanId(Long id);

    Celebrity save(final Celebrity celebrity);
}
