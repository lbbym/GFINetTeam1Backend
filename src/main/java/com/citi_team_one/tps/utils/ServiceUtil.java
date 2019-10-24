package com.citi_team_one.tps.utils;

import com.citi_team_one.tps.service.SalerDealsService;
import com.citi_team_one.tps.service.TraderDealsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ServiceUtil {
    @Autowired
    public SalerDealsService salerDealsService;
    @Autowired
    public TraderDealsService traderDealsService;

    public static ServiceUtil serviceUtil;

    @PostConstruct
    public void init() {
        serviceUtil = this;
    }
}
