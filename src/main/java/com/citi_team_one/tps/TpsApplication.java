package com.citi_team_one.tps;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.citi_team_one.tps.mapper")
public class TpsApplication {
    public static void main(String[] args) {
        SpringApplication.run(TpsApplication.class, args);
    }

}
