package com.ultlog.collector.health;

import com.ultlog.collector.appender.EsAppender;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.ultlog.common.constant.API.POST_HEALTH;

/**
 * @program: collector
 * @link: github.com/ultlog/collector
 * @author: will
 * @create: 2020-05-20
 **/
public class HealthSender {

    ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

    OkHttpClient client = new OkHttpClient();

    public static final Logger LOGGER = LoggerFactory.getLogger(HealthSender.class);

    public static synchronized HealthSender getInstance(String ulaUrl){


        return new HealthSender(ulaUrl);
    }

    private HealthSender(String ulaUrl){

        scheduledThreadPool.scheduleAtFixedRate(()->{

            Request request = new Request.Builder().url(ulaUrl+POST_HEALTH).post(RequestBody.create(EsAppender.MEDIA_TYPE_JSON_UTF8,"")).build();

            // post data to ula
            try {
                client.newCall(request).execute();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(),e);
            }

        },0,60, TimeUnit.SECONDS);
    }

}
