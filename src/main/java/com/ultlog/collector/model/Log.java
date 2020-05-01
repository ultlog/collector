package com.ultlog.collector.model;

import ch.qos.logback.classic.Level;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @program: collector
 * @link: github.com/ultlog/collector
 * @author: will
 * @create: 2020-05-01
 **/
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

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

    /**
     * log level
     */
    private String level;

    /**
     * create time
     */
    private LocalDateTime createTime;

    /**
     * log message
     */
    private String message;

    /**
     * error
     */
    private Exception exception;

    /**
     * uri which throws this exception
     */
    private String uri;

    public String getProject() {
        return project;
    }

    public Log setProject(String project) {
        this.project = project;
        return this;
    }

    public String getModule() {
        return module;
    }

    public Log setModule(String module) {
        this.module = module;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public Log setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getLevel() {
        return level;
    }

    public Log setLevel(String level) {
        this.level = level;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public Log setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Log setMessage(String message) {
        this.message = message;
        return this;
    }

    public Exception getException() {
        return exception;
    }

    public Log setException(Exception exception) {
        this.exception = exception;
        return this;
    }

    public String getUri() {
        return uri;
    }

    public Log setUri(String uri) {
        this.uri = uri;
        return this;
    }
}
