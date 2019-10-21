package com.citi_team_one.tps.mapper;

import com.citi_team_one.tps.model.TraderDeal;

import java.util.List;

public interface TraderDealMapper {
    List<TraderDeal> doFindAll();
    TraderDeal doAddTraderDeal(TraderDeal newDeal);
    TraderDeal doUpdateTraderDeal(TraderDeal newDeal);
}
