package com.ultlog.collector.controller;

import com.ultlog.common.model.HealthInfo;
import com.ultlog.common.model.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ultlog.common.constant.API.GET_HEALTH;

/**
 * @program: collector
 * @link: github.com/ultlog/collector
 * @author: will
 * @create: 2020-06-02
 **/
@RequestMapping(GET_HEALTH)
@RestController
public class HealthController {


    @RequestMapping("")
    public Result<HealthInfo> getHealth(){
        // todo init healthInfo
        return new Result<>(HttpStatus.OK.value(),"",new HealthInfo());
    }

}
