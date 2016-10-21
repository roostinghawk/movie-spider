package com.leadingsoft.spider.repository;


import com.leadingsoft.spider.model.Movie;
import com.leadingsoft.spider.model.MovieReview;
import org.springframework.data.repository.Repository;

/**
 * Created by liuw on 2016/9/30.
 */
public interface MovieReviewRepository extends Repository<MovieReview, Long> {

    MovieReview save(final MovieReview movie);
}
