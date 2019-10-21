package com.citi_team_one.tps.controller;


import com.citi_team_one.tps.model.SalerDeal;
import com.citi_team_one.tps.model.StatusCode;
import com.citi_team_one.tps.model.TraderDeal;
import com.citi_team_one.tps.service.SalerDealsService;
import com.citi_team_one.tps.service.TraderDealsService;
import com.citi_team_one.tps.utils.DealMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DealController {

    private boolean BO = true;
    @Autowired
    private TraderDealsService traderDealsService;
    @Autowired
    private SalerDealsService salerDealsService;

    @RequestMapping(value = "/tradeDeal", method = RequestMethod.POST)
    public TraderDeal addTradeDeal(TraderDeal traderDeal) {
        if (DealMatcher.getInstance().isMatch(traderDeal)) {
            //already TPS_PROCESSED, we send it to BO
            traderDeal.setStatus(BO ? StatusCode.ACCEPTED : StatusCode.REJECTED);
            traderDealsService.updateTraderDeal(traderDeal);
        }
        return traderDeal;
    }


    @RequestMapping(value = "/salerDeal", method = RequestMethod.POST)
    public SalerDeal addSalerDeal(SalerDeal salerDeal) {
        if (DealMatcher.getInstance().isMatch(salerDeal)) {
            //already TPS_PROCESSED, we send it to BO
            salerDeal.setStatus(BO ? StatusCode.ACCEPTED : StatusCode.REJECTED);
            salerDealsService.updateSalerDeal(salerDeal);
        }
        return salerDeal;
    }
}