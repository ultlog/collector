package com.ultlog.collector.appender;

import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultlog.common.model.Log;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.ultlog.common.constant.API.POST_LOG;

/**
 * @program: collector
 * @link: github.com/ultlog/collector
 * @author: will
 * @create: 2020-06-28
 **/
public class UlaAppender<E> extends UnsynchronizedAppenderBase<E> {
    /**
     * ula url
     */
    private String url;

    /**
     * name of project
     */
    private String project;

    /**
     * name of module
     */
    private String module;

    /**
     * uuid of instance(container)
     */
    private String uuid;

    OkHttpClient client = new OkHttpClient();

    public static final Logger LOGGER = LoggerFactory.getLogger(UlaAppender.class);

    public static final MediaType MEDIA_TYPE_JSON_UTF8 = MediaType.parse("application/json; charset=utf-8");

    private final static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void start() {
        started = true;
    }

    @Override
    protected void append(E eventObject) {

        LoggingEvent loggingEvent = (LoggingEvent) eventObject;
        Log log = new Log();

        log.setLevel(loggingEvent.getLevel().levelStr).setCreateTime(System.currentTimeMillis())
                .setProject(project).setMessage(loggingEvent.getMessage())
                .setModule(module).setUuid(uuid);

        final StackTraceElement[] callerDataArray = loggingEvent.getCallerData();
        final IThrowableProxy throwableProxy = loggingEvent.getThrowableProxy();
        StackTraceElementProxy[] stackTraceElementProxyArray = null;
        if (throwableProxy != null) {

            stackTraceElementProxyArray = throwableProxy.getStackTraceElementProxyArray();
        }

        // add stack
        if (callerDataArray != null && callerDataArray.length > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (StackTraceElement stackTraceElement : callerDataArray) {
                stringBuilder.append(stackTraceElement.toString()).append(";");
            }
            final StringBuilder replace = stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length() - 1, "");
            log.setStack(replace.toString());
        } else if (stackTraceElementProxyArray != null && stackTraceElementProxyArray.length > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (StackTraceElementProxy stackTraceElementProxy : stackTraceElementProxyArray) {
                stringBuilder.append(stackTraceElementProxy.toString()).append(";");
            }
            final StringBuilder replace = stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length() - 1, "");
            log.setStack(replace.toString());

        }

        // get json string
        final String json;
        try {
            json = mapper.writeValueAsString(log);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            return;
        }

        // create request with json
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON_UTF8, json);
        Request request = new Request.Builder().url(url + POST_LOG).post(body).build();

        // post data to ula
        try (Response execute = client.newCall(request).execute()) {
            if (!execute.isSuccessful()) {
                LOGGER.error("Failed to send, log is " + json);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
