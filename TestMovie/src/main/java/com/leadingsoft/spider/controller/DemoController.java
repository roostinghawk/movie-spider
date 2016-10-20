package com.leadingsoft.spider.controller;

import com.leadingsoft.spider.pipeline.MovieDbPipeline;
import com.leadingsoft.spider.processor.MovieProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

@RestController
@RequestMapping("/demo")
public class DemoController {

	@Autowired
	private MovieDbPipeline movieDbPipeline;

	@RequestMapping(value = "/crawl", method = RequestMethod.GET)
	public ResponseEntity<Void> regions() {
		Spider.create(new MovieProcessor())
				//.addUrl(urls.get(i))
				.addUrl("https://movie.douban.com/subject/26683290/")
						//.addPipeline(new MovieFilePipeline(Consts.ROOT_PATH + threadNum))
				.addPipeline(this.movieDbPipeline)
				.thread(1) // threadNum should be more than one
				.run();  // 多个线程跑会导致IP被封，暂时使用单线程的方法,单线程也会被封，需要考虑频率问题

		return ResponseEntity.ok().build();
	}

}
