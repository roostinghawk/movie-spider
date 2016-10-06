package com.leadingsoft.spider.pipeline;

import com.leadingsoft.spider.Consts;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.FilePipeline;

import java.io.*;
import java.util.List;

/**
 * Created by liuw on 2016/10/6.
 */
public class StartPagePipeline extends FilePipeline {
    public void process(ResultItems resultItems, Task task) {
        String path = Consts.ROOT_PATH + Consts.FILE_URLS;

        try {
            PrintWriter e = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.getFile(path)), "UTF-8"));

            List<String> urls =  resultItems.get("urls");
            urls.forEach(url -> {
                e.println(url);
            });

            e.println();
            e.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
