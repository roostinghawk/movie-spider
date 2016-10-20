package com.leadingsoft.spider;

import com.leadingsoft.spider.service.douban.DoubanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by liuw on 2016/10/6.
 */
//@Component
@SpringBootApplication
public class Application {
    public static final String URL_START = "https://movie.douban.com/tag";

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private DoubanService doubanService;

//    @Autowired
//    private static MovieDbPipeline movieDbPipeline;

//    public static void main(String[] args){
//        ApplicationContext ctx =
//                new AnnotationConfigApplicationContext("com.leadingsoft.spider"); // Use annotated beans from the specified package
//
//        Application main = ctx.getBean(Application.class);
//        main.doubanService.spiderMovie();
//    }

//    public static void main(String[] args) throws IOException {
//
//        // 取得导航页所有的子URL
//        Spider.create(new DoubanStartPageProcessor())
//                .addUrl(URL_START)
//                .addPipeline(new StartPagePipeline())
//                .run();
//
//        // 遍历所有的类型
//        Path path = FileSystems.getDefault().getPath(Consts.ROOT_PATH + Consts.FILE_URLS);;
//        List<String> urls = Files.readAllLines(path);
//
//        //for (int i = 5; i < urls.size(); i++) {
//        int i = 7;
//            int threadNum = i + 1;
//            Spider.create(new DoubanMovieProcessor())
//                    //.addUrl(urls.get(i))
//                    .addUrl("https://movie.douban.com/subject/26683290/")
//                    //.addPipeline(new MovieFilePipeline(Consts.ROOT_PATH + threadNum))
//                    .addPipeline(new MovieDbPipeline())
//                    .thread(1) // threadNum should be more than one
//                    .run();  // 多个线程跑会导致IP被封，暂时使用单线程的方法,单线程也会被封，需要考虑频率问题
//                    //.start(); // run async
//        //}
//    }

    public static void main(final String[] args) throws UnknownHostException {
        final SpringApplication app = new SpringApplication(Application.class);

        final Environment env = app.run(args).getEnvironment();
        Application.log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\thttp://127.0.0.1:{}\n\t" +
                        "External: \thttp://{}:{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"));

        final String configServerStatus = env.getProperty("configserver.status");
        Application.log.info("\n----------------------------------------------------------\n\t" +
                        "Config Server: \t{}\n----------------------------------------------------------",
                configServerStatus == null ? "Not found or not setup for this application" : configServerStatus);
    }


}
