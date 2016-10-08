package com.leadingsoft.spider;

import com.leadingsoft.spider.pipeline.MovieFilePipeline;
import com.leadingsoft.spider.pipeline.StartPagePipeline;
import com.leadingsoft.spider.processor.MovieProcessor;
import com.leadingsoft.spider.processor.StartPageProcessor;
import us.codecraft.webmagic.Spider;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by liuw on 2016/10/6.
 */
public class Application {
    public static final String URL_START = "https://movie.douban.com/tag";

    public static void main(String[] args) throws IOException {
        // 取得导航页所有的子URL
//        Spider.create(new StartPageProcessor())
//                .addUrl(URL_START)
//                .addPipeline(new StartPagePipeline())
//                .run();

        // 遍历所有的类型
        Path path = FileSystems.getDefault().getPath(Consts.ROOT_PATH + Consts.FILE_URLS);;
        List<String> urls = Files.readAllLines(path);

        //for (int i = 5; i < urls.size(); i++) {
        int i = 7;
            int threadNum = i + 1;
            Spider.create(new MovieProcessor())
                    .addUrl(urls.get(i))
                    .addPipeline(new MovieFilePipeline(Consts.ROOT_PATH + threadNum))
                    .thread(threadNum) // threadNum should be more than one
                    .run();  // 多个线程跑会导致IP被封，暂时使用单线程的方法,单线程也会被封，需要考虑频率问题
                    //.start(); // run async
        //}
    }

}
