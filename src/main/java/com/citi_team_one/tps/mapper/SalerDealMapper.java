package com.citi_team_one.tps.mapper;

import com.citi_team_one.tps.model.SalerDeal;

import java.util.List;

public interface SalerDealMapper {
    List<SalerDeal> doFindAll();
    SalerDeal doAddSalerDeal(SalerDeal newDeal);
    SalerDeal doUpdateSalerDeal(SalerDeal newDeal);
}
