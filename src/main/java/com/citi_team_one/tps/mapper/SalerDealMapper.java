package com.citi_team_one.tps.mapper;

import com.citi_team_one.tps.model.SalerDeal;
import com.citi_team_one.tps.model.TraderDeal;

import java.util.List;

public interface SalerDealMapper {
    List<SalerDeal> doFindAllInPages(Integer pageNum, Integer perPage);
    SalerDeal doAddSalerDeal(SalerDeal newDeal);
    SalerDeal doUpdateSalerDeal(SalerDeal newDeal);
}
