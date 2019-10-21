package com.citi_team_one.tps.mapper;

import com.citi_team_one.tps.model.TraderDeal;

import java.util.List;

public interface TraderDealMapper {
    List<TraderDeal> doFindAll();//分页查询：PageHelper.startPage(page,pagesize); 后面直接跟查询
    TraderDeal doAddTraderDeal(TraderDeal newDeal);
    TraderDeal doUpdateTraderDeal(TraderDeal newDeal);
}
