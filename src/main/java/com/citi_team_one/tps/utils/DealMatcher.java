package com.citi_team_one.tps.utils;

import com.citi_team_one.tps.model.SalerDeal;
import com.citi_team_one.tps.model.StatusCode;
import com.citi_team_one.tps.model.TraderDeal;
import com.citi_team_one.tps.service.SalerDealsService;
import com.citi_team_one.tps.service.TraderDealsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@Component
public class DealMatcher {
    @Autowired
    private SalerDealsService salerDealsService;
    @Autowired
    private TraderDealsService traderDealsService;

    BlockingQueue<TraderDeal> traderDealList = new LinkedBlockingDeque<>();
    BlockingQueue<SalerDeal> salerDealList = new LinkedBlockingDeque<>();

    private static DealMatcher singletonDealMatcher = new DealMatcher();
    private BOVerify BOVerify = new BOVerify();

    private DealMatcher() {
    }

    @PostConstruct
    public void init() {
        singletonDealMatcher.salerDealsService = this.salerDealsService;
        singletonDealMatcher.traderDealsService = this.traderDealsService;
    }

    public static DealMatcher getInstance() {
        return singletonDealMatcher;
    }


    public TraderDeal isMatch(SalerDeal salerDeal) throws JMSException {
        salerDealsService.addSalerDeal(salerDeal);
        salerDealList.add(salerDeal);
        synchronized (traderDealList){
            for( TraderDeal traderDeal : traderDealList){
                if(salerDeal.getTradeSender().equals(traderDeal.getTradeReciver()) &&
                   salerDeal.getProductId().equals(traderDeal.getProductId()) &&
                   salerDeal.getVolume().equals(traderDeal.getVolume())){
                    if(salerDeal.getPrice().equals(traderDeal.getPrice())){
                        salerDeal.setStatus(StatusCode.TPS_PROCESSED);
                        salerDealsService.updateSalerDeal(salerDeal);
                        traderDeal.setStatus(StatusCode.TPS_PROCESSED);
                        traderDealsService.updateTraderDeal(traderDeal);
                        BOVerify.getBOVerifyMsg(salerDeal, traderDeal);
                        salerDealList.remove(salerDeal);
                        traderDealList.remove(traderDeal);
                        // if the price match, return the trader deal
                        return traderDeal;
                    } else {
                        salerDeal.setStatus(StatusCode.PRICE_UNMATCHED);
                        salerDealsService.updateSalerDeal(salerDeal);
                        // if the price dont match, return the trader deal
                        return traderDeal;
                    }
                }
            }
        }
        //TODO version number issue! this need to be the first
        salerDeal.setStatus(StatusCode.PENDING);
        salerDealsService.updateSalerDeal(salerDeal);
        // if the deal is still pending, return null
        return null;
    }


    public SalerDeal isMatch(TraderDeal traderDeal) throws JMSException {
        traderDealsService.addTraderDeal(traderDeal);
        traderDealList.add(traderDeal);
        synchronized (salerDealList){
            for( SalerDeal salerDeal : salerDealList){
                if(traderDeal.getTradeSender().equals(salerDeal.getTradeReciver()) &&
                   traderDeal.getProductId().equals(salerDeal.getProductId()) &&
                   traderDeal.getVolume().equals(salerDeal.getVolume())){
                    if(traderDeal.getPrice().equals(salerDeal.getPrice())){
                        traderDeal.setStatus(StatusCode.TPS_PROCESSED);
                        traderDealsService.updateTraderDeal(traderDeal);
                        salerDeal.setStatus(StatusCode.TPS_PROCESSED);
                        salerDealsService.updateSalerDeal(salerDeal);
                        BOVerify.getBOVerifyMsg(salerDeal, traderDeal);
                        salerDealList.remove(salerDeal);
                        traderDealList.remove(traderDeal);
                        // if the price match, return the saler deal
                        return salerDeal;
                    } else {
                        traderDeal.setStatus(StatusCode.PRICE_UNMATCHED);
                        traderDealsService.updateTraderDeal(traderDeal);
                        // if the price dont match, return the trader deal
                        return salerDeal;
                    }
                }
            }
        }
        traderDeal.setStatus(StatusCode.PENDING);
        traderDealsService.updateTraderDeal(traderDeal);
        // if the deal is still pending, return null
        return null;
    }
}
