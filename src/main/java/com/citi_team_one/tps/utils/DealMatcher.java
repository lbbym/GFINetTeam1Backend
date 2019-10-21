package com.citi_team_one.tps.utils;

import com.citi_team_one.tps.model.SalerDeal;
import com.citi_team_one.tps.model.TraderDeal;
import com.citi_team_one.tps.service.SalerDealsService;
import com.citi_team_one.tps.service.TraderDealsService;
import org.springframework.beans.factory.annotation.Autowired;

public class DealMatcher {
    @Autowired
    private SalerDealsService salerDealsService;

    @Autowired
    private TraderDealsService traderDealsService;

    public Boolean isMatch(SalerDeal salerDeal)
    {
        //TODO
        return false;
    }
    public Boolean isMatch(TraderDeal traderDeal)
    {
        //TODO
        return false;
    }
}
