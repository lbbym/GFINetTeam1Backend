package com.citi_team_one.tps.controller;


import com.alibaba.fastjson.JSONObject;
import com.citi_team_one.tps.auth.JWTUtil;
import com.citi_team_one.tps.model.ResponseBean;
import com.citi_team_one.tps.model.SalerDeal;
import com.citi_team_one.tps.model.StatusCode;
import com.citi_team_one.tps.model.TraderDeal;
import com.citi_team_one.tps.service.SalerDealsService;
import com.citi_team_one.tps.service.TraderDealsService;
import com.citi_team_one.tps.utils.DealMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class DealController {

    private boolean BO = true;
    @Autowired
    private TraderDealsService traderDealsService;
    @Autowired
    private SalerDealsService salerDealsService;

    @RequestMapping(value = "/traderDeal", method = RequestMethod.GET)
    public ResponseBean getTraderDeals(@RequestParam("pageNum") Integer pageNum,
                                       @RequestParam("perPage") Integer perPage) {
        JSONObject json_result = new JSONObject(true);
        json_result.put("traderDealList", traderDealsService.findAllInPages(pageNum, perPage));
        return new ResponseBean(200, "success", json_result);
    }

    @RequestMapping(value = "/traderDeal", method = RequestMethod.POST)
    public TraderDeal addTraderDeal(TraderDeal traderDeal) {
        SalerDeal salerDeal = DealMatcher.getInstance().isMatch(traderDeal);
        if (salerDeal.getStatus().equals(StatusCode.TPS_PROCESSED)) {
            //already TPS_PROCESSED, we send it to BO
            salerDeal.setStatus(BO ? StatusCode.ACCEPTED : StatusCode.REJECTED);
            traderDeal.setStatus(BO ? StatusCode.ACCEPTED : StatusCode.REJECTED);

            salerDealsService.updateSalerDeal(salerDeal);
            traderDealsService.updateTraderDeal(traderDeal);
        }
        return traderDeal;
    }

    //////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/salerDeal", method = RequestMethod.GET)
    public ResponseBean getSalerDeals(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("perPage") Integer perPage) {
        JSONObject json_result = new JSONObject(true);
        json_result.put("traderDealList", salerDealsService.findAllInPages(pageNum, perPage));
        return new ResponseBean(200, "success", json_result);
    }

    @RequestMapping(value = "/salerDeal", method = RequestMethod.POST)
    public SalerDeal addSalerDeal(SalerDeal salerDeal) {
        TraderDeal traderDeal = DealMatcher.getInstance().isMatch(salerDeal);
        if (traderDeal.getStatus().equals(StatusCode.TPS_PROCESSED)) {
            //already TPS_PROCESSED, we send it to BO
            salerDeal.setStatus(BO ? StatusCode.ACCEPTED : StatusCode.REJECTED);
            traderDeal.setStatus(BO ? StatusCode.ACCEPTED : StatusCode.REJECTED);

            salerDealsService.updateSalerDeal(salerDeal);
            traderDealsService.updateTraderDeal(traderDeal);
        }
        return salerDeal;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }
}