package com.leadingsoft.spider.pipeline;

import com.leadingsoft.spider.model.Movie;
import com.leadingsoft.spider.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.PageModelPipeline;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Created by liuw on 2016/10/4.
 */
@Component("MovieDbPipeline")
public class MovieDbPipeline implements Pipeline {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public void process(ResultItems resultItems, Task task){
        Movie movie = (Movie)resultItems.get("movie");
        if(movie != null) {
            this.movieRepository.save(movie);
        }
    }
}
