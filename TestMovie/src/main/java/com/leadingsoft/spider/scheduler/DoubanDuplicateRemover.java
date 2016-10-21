package com.leadingsoft.spider.scheduler;

import com.leadingsoft.spider.model.Movie;
import com.leadingsoft.spider.processor.douban.DoubanMovieProcessor;
import com.leadingsoft.spider.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.DuplicateRemovedScheduler;
import us.codecraft.webmagic.scheduler.component.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.component.DuplicateRemover;

/**
 * Created by liuw on 2016/10/21.
 */
@Component
public class DoubanDuplicateRemover extends BloomFilterDuplicateRemover {

    @Autowired
    MovieRepository movieRepository;

    public DoubanDuplicateRemover(){
        super(1000000);
    }

    public DoubanDuplicateRemover(int expectedInsertions) {
        super(expectedInsertions);
    }

    @Override
    public boolean isDuplicate(Request request, Task task) {
        Movie movie = this.movieRepository.findOneByUrl(request.getUrl());

        if(movie != null) {
            return true;
        }

        return false;
    }
}
