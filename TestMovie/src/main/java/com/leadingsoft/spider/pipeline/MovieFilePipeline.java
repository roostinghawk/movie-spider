package com.leadingsoft.spider.pipeline;

import com.leadingsoft.spider.model.Movie;
import org.apache.commons.codec.digest.DigestUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.FilePipeline;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by liuw on 2016/10/4.
 */
public class MovieFilePipeline extends FilePipeline {

    public MovieFilePipeline (String path) {
        super(path);
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String path = this.path + "output.csv";

        try {

            Movie movie = resultItems.get("movie");
            if(movie == null) {
                return;
            }

            PrintWriter e = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.getFile(path), true), "UTF-8"));

            e.print(movie.getName() + "\t");
            e.print(movie.getUrl() + "\t");
//            e.print(movie.getDirector() + "\t");
//            e.print(movie.getWriter() + "\t");
//            e.print(movie.getActor() + "\t");
//            e.print(movie.getGenre() + "\t");
//            e.print(movie.getReleaseDate() + "\t");
//            e.print(movie.getImdb() + "\t");
//            e.print(movie.getRunTime() + "\t");
//            e.print(movie.getZone() + "\t");
//            e.print(movie.getLanguage() + "\t");
//            e.print(movie.getOtherName() + "\t");
//            e.print(movie.getRating() + "\t");
//            e.print(movie.getRatingCount() + "\t");
//            e.print(movie.getFiveStarRating() + "\t");
//            e.print(movie.getCommentCount() + "\t");
//            e.print(movie.getReviewCount() + "\t");

//            resultItems.getAll().entrySet().forEach(item -> {
//                e.print(item.getValue() + "\t");
//            });

            e.println();
            e.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


}
