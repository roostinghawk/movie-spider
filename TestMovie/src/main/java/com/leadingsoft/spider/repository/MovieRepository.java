package com.leadingsoft.spider.repository;


import com.leadingsoft.spider.model.Movie;
import org.springframework.data.repository.Repository;

/**
 * Created by liuw on 2016/9/30.
 */
@org.springframework.stereotype.Repository
public interface MovieRepository extends Repository<Movie, Long> {
    Movie save(final Movie movie);
}
