package com.leadingsoft.spider.service.impl;

import com.leadingsoft.spider.pipeline.MovieDbPipeline;
import com.leadingsoft.spider.processor.MovieProcessor;
import com.leadingsoft.spider.service.DoubanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.inject.Qualifier;

/**
 * Created by liuw on 2016/10/19.
 */
@Service
public class DoubanServiceImpl implements DoubanService {

    @Autowired(required = false)
    private Pipeline movieDbPipeline;

    @Override
    public void spiderMovie(){
        Spider.create(new MovieProcessor())
                //.addUrl(urls.get(i))
                .addUrl("https://movie.douban.com/subject/26683290/")
                        //.addPipeline(new MovieFilePipeline(Consts.ROOT_PATH + threadNum))
                .addPipeline(this.movieDbPipeline)
                .thread(1) // threadNum should be more than one
                .run();  // 多个线程跑会导致IP被封，暂时使用单线程的方法,单线程也会被封，需要考虑频率问题
    }
}
