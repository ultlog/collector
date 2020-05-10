package com.ultlog.collector.support;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @program: collector
 * @link: github.com/ultlog/collector
 * @author: will
 * @create: 2020-05-10
 **/
public interface ThreadPoolProvider {

    ThreadPoolExecutor getThreadPoolExecutor();
}
