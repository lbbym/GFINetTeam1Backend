package com.citi_team_one.tps.utils;

import com.citi_team_one.tps.model.SalerDeal;
import com.citi_team_one.tps.model.TraderDeal;
import com.citi_team_one.tps.service.SalerDealsService;
import com.citi_team_one.tps.service.TraderDealsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class DealMatcher {
    @Autowired
    private SalerDealsService salerDealsService;
    @Autowired
    private TraderDealsService traderDealsService;

    List<TraderDeal> traderDealList = new ArrayList<>();
    List<SalerDeal> salerDealList = new ArrayList<>();

    private static DealMatcher singletonDealMatcher = new DealMatcher();

    private DealMatcher() {
    }

    public static DealMatcher getInstance() {
        return singletonDealMatcher;
    }

    public synchronized Boolean isMatch(SalerDeal salerDeal) {
        //TODO
        return false;
    }

    public synchronized Boolean isMatch(TraderDeal traderDeal) {
        //TODO
        return false;
    }
}
