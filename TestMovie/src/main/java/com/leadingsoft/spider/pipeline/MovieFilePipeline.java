package com.leadingsoft.spider.pipeline;

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

    public void process(ResultItems resultItems, Task task) {
        String path = this.path + "output.csv";

        try {
            PrintWriter e = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.getFile(path), true), "UTF-8"));

            resultItems.getAll().entrySet().forEach(item -> {
                e.print(item.getValue() + "\t");
            });

            e.println();
            e.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


}
