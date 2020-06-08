package com.ultlog.collector.support;

import java.util.UUID;

/**
 * @program: collector
 * @link: github.com/ultlog/collector
 * @author: will
 * @create: 2020-05-25
 **/
public class DefaultUuidProducer implements UuidProducer {

    @Override
    public String getUuid() {
        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return idd[0];
    }
}
