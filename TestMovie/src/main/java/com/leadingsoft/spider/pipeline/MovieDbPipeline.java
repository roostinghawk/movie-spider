package com.leadingsoft.spider.pipeline;

import com.leadingsoft.spider.dto.MovieDTO;
import com.leadingsoft.spider.model.Celebrity;
import com.leadingsoft.spider.model.Movie;
import com.leadingsoft.spider.model.MovieReview;
import com.leadingsoft.spider.repository.CelebrityRepository;
import com.leadingsoft.spider.repository.MovieRepository;
import com.leadingsoft.spider.repository.MovieReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.PageModelPipeline;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.transaction.Transactional;
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

    @Autowired
    private CelebrityRepository celebrityRepository;

    @Autowired
    private MovieReviewRepository movieReviewRepository;

    @Override
    @Transactional
    public void process(ResultItems resultItems, Task task){
        MovieDTO dto = resultItems.get("movie");
        if(dto != null) {

            // 检查是否存在
            Movie existingMovie = this.movieRepository.findOneByDoubanId(dto.getDoubanId());

            if(existingMovie != null) {
                return;
            }

            Movie movie = new Movie();

            // 导演
            dto.getDirectorList().forEach(director -> {
                Celebrity celebrity = new Celebrity();
                celebrity.setDoubanId(director.getDoubanId());
                celebrity.setUrl(director.getUrl());
                celebrity.setName(director.getName());
                movie.getDirectorList().add(this.saveWithCheck(celebrity));
            });

            // 编剧
            dto.getWriterList().forEach(writer -> {
                Celebrity celebrity = new Celebrity();
                celebrity.setDoubanId(writer.getDoubanId());
                celebrity.setUrl(writer.getUrl());
                celebrity.setName(writer.getName());
                movie.getWriterList().add(this.saveWithCheck(celebrity));
            });

            // 主演
            dto.getActorList().forEach(actor -> {
                Celebrity celebrity = new Celebrity();
                celebrity.setDoubanId(actor.getDoubanId());
                celebrity.setUrl(actor.getUrl());
                celebrity.setName(actor.getName());
                movie.getActorList().add(this.saveWithCheck(celebrity));
            });

            movie.setName(dto.getName());
            movie.setUrl(dto.getUrl());
            movie.setDoubanId(dto.getDoubanId());
            movie.setGenre(dto.getGenre());
            movie.setReleaseDate(dto.getReleaseDate());
            movie.setImdb(dto.getImdb());
            movie.setRunTime(dto.getRunTime());
            movie.setZone(dto.getZone());
            movie.setLanguage(dto.getLanguage());
            movie.setOtherName(dto.getOtherName());

            // 评论相关
            MovieReview movieReview = new MovieReview();
            movieReview.setRating(dto.getRating());
            movieReview.setRatingCount(dto.getRatingCount());
            movieReview.setFiveStarRating(dto.getFiveStarRating());
            movieReview.setCommentCount(dto.getCommentCount());
            movieReview.setReviewCount(dto.getReviewCount());
            movieReview.setDoubanId(dto.getDoubanId());

            // 保存评论
            movie.setMovieReview(this.movieReviewRepository.save(movieReview));

            // 保存电影
            this.movieRepository.save(movie);
        }
    }

    /**
     * 检查并保存Movie
     * @param movie
     * @return
     */
    private Movie checkMovie(Movie movie){
        Movie existingModel = this.movieRepository.findOneByDoubanId(movie.getDoubanId());
        if(existingModel != null) {
            return existingModel;
        }

        return this.movieRepository.save(movie);
    }

    /**
     * 检查并保存Celebrity
     * @param celebrity
     * @return
     */
    private Celebrity saveWithCheck(Celebrity celebrity){
        Celebrity existingModel = this.celebrityRepository.findOneByDoubanId(celebrity.getDoubanId());
        if(existingModel != null) {
            return existingModel;
        }

        return this.celebrityRepository.save(celebrity);
    }
}
