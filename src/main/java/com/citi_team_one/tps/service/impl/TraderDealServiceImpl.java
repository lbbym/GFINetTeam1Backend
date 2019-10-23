package com.citi_team_one.tps.service.impl;

import com.citi_team_one.tps.mapper.TraderDealMapper;
import com.citi_team_one.tps.model.StatusCode;
import com.citi_team_one.tps.model.TraderDeal;
import com.citi_team_one.tps.service.SalerDealsService;
import com.citi_team_one.tps.service.TraderDealsService;
import com.citi_team_one.tps.utils.DealMatcher;
import com.github.pagehelper.PageHelper;
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
    public TraderDeal findById(Integer id) {
        return traderDealMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TraderDeal> findAllBySenderId(Integer senderId) {
        return  traderDealMapper.doFindAllBySenderId(senderId);
    }

    @Override
    public List<TraderDeal> findAllInPages(Integer pageNum, Integer perPage) {
        PageHelper.startPage(pageNum, perPage);
        return traderDealMapper.doFindAll();
    }

    @Override
    public TraderDeal addTraderDeal(TraderDeal newDeal) {
        newDeal.setTxnI(null);
        traderDealMapper.doAddTraderDeal(newDeal);
        return newDeal;
    }

    @Override
    public TraderDeal updateTraderDeal(TraderDeal updatedDeal) {
        updatedDeal.setInterVNum(updatedDeal.getInterVNum() + 1);
        System.out.println("!!!!!!!!!!!!!!");
        System.out.println(traderDealMapper.doUpdateTraderDeal(updatedDeal));
        return updatedDeal;
    }
}
