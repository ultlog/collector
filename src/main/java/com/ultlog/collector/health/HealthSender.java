package com.ultlog.collector.health;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.istack.internal.NotNull;
import com.ultlog.collector.appender.EsAppender;
import com.ultlog.common.exception.FailedGetIpException;
import com.ultlog.common.exception.FailedGetJsonException;
import com.ultlog.common.model.HealthInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
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

    public static synchronized void init(@NotNull String ulaUrl, @NotNull String project, @NotNull String module, @NotNull String uuid) {
        if (healthSender == null) {
            healthSender = new HealthSender(ulaUrl, project, module, uuid);
        }
    }

    private static HealthSender healthSender = null;

    private HealthSender(String ulaUrl, String project, String module, String uuid) {
        final String hostAddress;
        try {
            hostAddress = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FailedGetIpException("Failed to get ip");
        }

        // todo get port[with docker]
        String port = "8080";

        final HealthInfo healthInfo = new HealthInfo();
        healthInfo.setIp(hostAddress);
        healthInfo.setPort(port);
        healthInfo.setProject(project);
        healthInfo.setModule(module);
        healthInfo.setUuid(uuid);
        final ObjectMapper mapper = new ObjectMapper();
        final String s;
        try {
            s = mapper.writeValueAsString(healthInfo);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new FailedGetJsonException("Failed to get json");
        }
        scheduledThreadPool.scheduleAtFixedRate(() -> {

            Request request = new Request.Builder().url(ulaUrl + POST_HEALTH).post(RequestBody.create(EsAppender.MEDIA_TYPE_JSON_UTF8, s)).build();

            try {
                client.newCall(request).execute();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }

        }, 0, 60, TimeUnit.SECONDS);
    }

}
