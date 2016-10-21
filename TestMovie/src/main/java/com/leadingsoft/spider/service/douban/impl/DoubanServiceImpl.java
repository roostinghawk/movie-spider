package com.leadingsoft.spider.service.douban.impl;

import com.leadingsoft.spider.Consts;
import com.leadingsoft.spider.pipeline.MovieFilePipeline;
import com.leadingsoft.spider.processor.douban.DoubanMovieProcessor;
import com.leadingsoft.spider.scheduler.DoubanDuplicateRemover;
import com.leadingsoft.spider.service.douban.DoubanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.component.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.component.HashSetDuplicateRemover;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by liuw on 2016/10/19.
 */
@Service
public class DoubanServiceImpl implements DoubanService {

    @Autowired(required = false)
    private Pipeline movieDbPipeline;

    @Autowired
    private DoubanDuplicateRemover doubanDuplicateRemover;

    @Override
    public void crawlMovie() throws IOException {
//        // 遍历所有的类型
//        Path path = FileSystems.getDefault().getPath(Consts.ROOT_PATH + Consts.FILE_URLS);;
//        List<String> urls = Files.readAllLines(path);
//
//        for (int i = 0; i < urls.size(); i++) {
//            Spider.create(new DoubanMovieProcessor())
//                    .addUrl(urls.get(i))
//                            // .addPipeline(new MovieFilePipeline(Consts.ROOT_PATH + "test"))
//                    .addPipeline(this.movieDbPipeline)
//                    .thread(1)
//                    .setScheduler(new FileCacheQueueScheduler(Consts.ROOT_PATH))
//                            //.setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000000)))
//                    .run();
//        }

        Spider.create(new DoubanMovieProcessor())
                .addUrl("https://movie.douban.com/subject/26683290/")
                .addUrl("https://movie.douban.com/subject/26683290/")
                        .addUrl("https://movie.douban.com/subject/25827935/")
                        // .addPipeline(new MovieFilePipeline(Consts.ROOT_PATH + "test"))
                .addPipeline(this.movieDbPipeline)
                .thread(1)
                //.setScheduler(new FileCacheQueueScheduler(Consts.ROOT_PATH))
                .setScheduler(new QueueScheduler().setDuplicateRemover(this.doubanDuplicateRemover))
                .run();
    }
}
