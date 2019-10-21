package com.citi_team_one.tps.service.impl;

import com.citi_team_one.tps.mapper.TraderDealMapper;
import com.citi_team_one.tps.model.StatusCode;
import com.citi_team_one.tps.model.TraderDeal;
import com.citi_team_one.tps.service.SalerDealsService;
import com.citi_team_one.tps.service.TraderDealsService;
import com.citi_team_one.tps.utils.DealMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TraderDealServiceImpl implements TraderDealsService {
    @Autowired
    private TraderDealMapper traderDealMapper;
    @Autowired
    private SalerDealsService salerDealsService;

    @Override
    public List<TraderDeal> findAllInPages(Integer pageNum, Integer perPage) {
        return traderDealMapper.doFindAllInPages(pageNum, perPage);
    }

    @Override
    public TraderDeal addTraderDeal(TraderDeal newDeal) {
        return traderDealMapper.doAddTraderDeal(checkMatch(newDeal));
    }

    @Override
    public TraderDeal updateTraderDeal(TraderDeal updatedDeal){
        return traderDealMapper.doUpdateTraderDeal(checkMatch(updatedDeal));
    }

    private TraderDeal checkMatch(TraderDeal newDeal) {
        if(new DealMatcher().isMatch(newDeal))
        {
            newDeal.setStatus(StatusCode.TPS_PROCESSED);
        }
        else
            newDeal.setStatus(StatusCode.PENDING);
        return newDeal;
    }
}