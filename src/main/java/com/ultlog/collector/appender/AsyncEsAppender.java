package com.ultlog.collector.appender;

import com.ultlog.collector.support.ThreadPoolProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @program: collector
 * @link: github.com/ultlog/collector
 * @author: will
 * @create: 2020-05-10
 **/
@Deprecated
public class AsyncEsAppender<E> extends EsAppender<E> {


    private ThreadPoolExecutor threadPoolExecutor;

    private String threadPoolProviderName;

    public static final Logger LOGGER = LoggerFactory.getLogger(AsyncEsAppender.class);

    @Override
    protected void append(E eventObject) {
        if (threadPoolExecutor == null || threadPoolProviderName == null) {
            LOGGER.error("'threadPoolExecutor' might not have been initialized,check prop 'ThreadPoolProviderName'");
            return;
        }
        threadPoolExecutor.execute(() -> super.append(eventObject));
    }

    public void setThreadPoolProviderName(String threadPoolProviderName) {

        try {
            final Class<ThreadPoolProvider> aClass = (Class<ThreadPoolProvider>) Class.forName(threadPoolProviderName);
            final ThreadPoolProvider threadPoolProvider = aClass.newInstance();
            this.threadPoolExecutor = threadPoolProvider.getThreadPoolExecutor();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
            LOGGER.error("'threadPoolExecutor' might not have been initialized,check prop 'ThreadPoolProviderName'");
        }
        this.threadPoolProviderName = threadPoolProviderName;
    }

    public String getThreadPoolProviderName() {
        return threadPoolProviderName;
    }
}
