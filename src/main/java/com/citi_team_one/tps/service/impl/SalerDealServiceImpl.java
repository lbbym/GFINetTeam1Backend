package com.citi_team_one.tps.service.impl;

import com.citi_team_one.tps.mapper.SalerDealMapper;
import com.citi_team_one.tps.model.SalerDeal;
import com.citi_team_one.tps.model.StatusCode;
import com.citi_team_one.tps.service.SalerDealsService;
import com.citi_team_one.tps.utils.DealMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalerDealServiceImpl implements SalerDealsService {
    @Autowired
    private SalerDealMapper salerDealMapper;


    @Override
    public List<SalerDeal> findAllInPages(Integer pageNum, Integer perPage) {
        return salerDealMapper.doFindAllInPages(pageNum, perPage);
    }

    @Override
    public SalerDeal addSalerDeal(SalerDeal newDeal) {
        return salerDealMapper.doAddSalerDeal(checkMatch(newDeal));
    }

    @Override
    public SalerDeal updateSalerDeal(SalerDeal updatedDeal) {
        return salerDealMapper.doUpdateSalerDeal(checkMatch(updatedDeal));
    }

    private SalerDeal checkMatch(SalerDeal newDeal) {
        if(DealMatcher.getInstance().isMatch(newDeal))
        {
            newDeal.setStatus(StatusCode.TPS_PROCESSED);
        }
        else
            newDeal.setStatus(StatusCode.PENDING);
        return newDeal;
    }
}
