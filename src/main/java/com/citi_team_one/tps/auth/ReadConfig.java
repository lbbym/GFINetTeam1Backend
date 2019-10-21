package com.citi_team_one.tps.auth;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@Data
public class ReadConfig {
    @Value("${expire_time_minute}")
    public long expire_time_minute;

    @PostConstruct
    public void init(){
        JWTUtil.setConfigInfo(this);
    }

}
