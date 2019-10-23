package com.citi_team_one.tps.mapper;

import com.citi_team_one.tps.model.TraderDeal;

import java.util.List;

public interface TraderDealMapper {
    TraderDeal doFindByTxnI(Integer txnI);
    List<TraderDeal> doFindAllBySenderId(Integer senderId);
    List<TraderDeal> doFindAll();
    Integer doAddTraderDeal(TraderDeal newDeal);
    Integer doUpdateTraderDeal(TraderDeal newDeal);
}
