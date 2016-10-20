package com.leadingsoft.spider.controller;

import com.leadingsoft.bizfuse.common.web.dto.result.ResultDTO;
import com.leadingsoft.bizfuse.common.web.dto.result.ResultError;
import com.leadingsoft.spider.service.douban.DoubanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/demo")
public class SpiderController {

	@Autowired
	private DoubanService doubanService;

	@RequestMapping(value = "/crawl/douban", method = RequestMethod.GET)
	public ResultDTO<Void> crawlDouban() throws IOException {
//        try {
            this.doubanService.crawlMovie();
//        } catch (Exception ex){
//            ResultError error = new ResultError();
//            error.setErrmsg(ex.getMessage());
//            return ResultDTO.failure(error);
//        }

		return ResultDTO.success();
	}

}
