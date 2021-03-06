package com.leadingsoft.spider.repository;


import com.leadingsoft.spider.model.Movie;
import org.springframework.data.repository.Repository;

/**
 * Created by liuw on 2016/9/30.
 */
public interface MovieRepository extends Repository<Movie, Long> {

    Movie findOneByDoubanId(Long id);

    Movie findOneByUrl(String id);

    Movie save(final Movie movie);
}
