package com.citi_team_one.tps.mapper;

import com.citi_team_one.tps.model.TraderDeal;
import com.citi_team_one.tps.model.User;

import java.util.List;

public interface TraderDealMapper {
    List<TraderDeal> doFindAllInPages(Integer pageNum, Integer perPage);
    TraderDeal doAddTraderDeal(TraderDeal newDeal);
    TraderDeal doUpdateTraderDeal(TraderDeal newDeal);
}
