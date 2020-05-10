package com.ultlog.collector.support;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: collector
 * @link: github.com/ultlog/collector
 * @author: will
 * @create: 2020-05-10
 **/
public class DefaultThreadPoolProvider implements ThreadPoolProvider {

    private AtomicInteger threadNumber = new AtomicInteger(0);

    @Override
    public ThreadPoolExecutor getThreadPoolExecutor() {
        return new ThreadPoolExecutor(5, 6, 2, TimeUnit.DAYS, new LinkedBlockingDeque<>(200), r -> {
            final Thread thread = new Thread(r);
            thread.setName("ultlog-collector-default-thread" + threadNumber.getAndIncrement());
            return thread;
        }, new ThreadPoolExecutor.AbortPolicy());
    }
}
