package com.ultlog.collector.appender;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ultlog.collector.model.Log;
import okhttp3.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @program: collector
 * @link: github.com/ultlog/collector
 * @author: will
 * @create: 2020-05-01
 **/
public class CollectorAppender<E> extends UnsynchronizedAppenderBase<E> {

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


    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("application/json; charset=utf-8");

    private final static ObjectMapper mapper = new ObjectMapper();

    static {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        mapper.registerModule(javaTimeModule);
    }

    @Override
    protected void append(E eventObject) {

        LoggingEvent loggingEvent = (LoggingEvent) eventObject;
        Log log = new Log();

        log.setLevel(loggingEvent.getLevel().levelStr).setCreateTime(System.currentTimeMillis())
                .setProject(project).setMessage(loggingEvent.getMessage())
                .setModule(module).setUuid(uuid);

        final StackTraceElement[] callerDataArray = loggingEvent.getCallerData();
        if (callerDataArray != null && callerDataArray.length > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (StackTraceElement stackTraceElement : callerDataArray) {
                stringBuilder.append(stackTraceElement.toString()).append(";");
            }
            final StringBuilder replace = stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length() - 1, "");
            log.setStack(replace.toString());
        }

        // get json string
        final String json;
        try {
            json = mapper.writeValueAsString(log);
        } catch (JsonProcessingException e) {
            // todo add fallback
            e.printStackTrace();
            return;
        }

        // create request with json
        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN, json);
        Request request = new Request.Builder().url(url).post(body).build();

        // post data to ula
        try (Response execute = client.newCall(request).execute()) {
            if (!execute.isSuccessful()) {
                // todo add fallback
            }
        } catch (IOException e) {
            // todo add fallback
            e.printStackTrace();
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
