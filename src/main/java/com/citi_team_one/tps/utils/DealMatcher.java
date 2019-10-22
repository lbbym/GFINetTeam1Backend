package com.citi_team_one.tps.utils;

import com.citi_team_one.tps.model.SalerDeal;
import com.citi_team_one.tps.model.StatusCode;
import com.citi_team_one.tps.model.TraderDeal;
import com.citi_team_one.tps.service.SalerDealsService;
import com.citi_team_one.tps.service.TraderDealsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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

    public StatusCode isMatch(SalerDeal salerDeal) {
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
                        salerDealList.remove(salerDeal);
                        traderDealList.remove(traderDeal);
                        return StatusCode.TPS_PROCESSED;
                    } else {
                        salerDeal.setStatus(StatusCode.PRICE_UNMATCHED);
                        salerDealsService.updateSalerDeal(salerDeal);
                        return StatusCode.PRICE_UNMATCHED;
                    }
                }
            }
        }
        salerDeal.setStatus(StatusCode.PENDING);
        salerDealsService.updateSalerDeal(salerDeal);
        return StatusCode.PENDING;
    }

    public StatusCode isMatch(TraderDeal traderDeal) {
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
                        salerDealList.remove(salerDeal);
                        traderDealList.remove(traderDeal);
                        return StatusCode.TPS_PROCESSED;
                    } else {
                        traderDeal.setStatus(StatusCode.PRICE_UNMATCHED);
                        traderDealsService.updateTraderDeal(traderDeal);
                        return StatusCode.PRICE_UNMATCHED;
                    }
                }
            }
        }
        traderDeal.setStatus(StatusCode.PENDING);
        traderDealsService.updateTraderDeal(traderDeal);
        return StatusCode.PENDING;
    }
}
