package com.citi_team_one.tps.mapper;

import com.citi_team_one.tps.model.SalerDeal;

import java.util.List;

public interface SalerDealMapper {
    List<SalerDeal> doFindAll();

    SalerDeal doFindByTxnI(Integer txnI);

    SalerDeal doFindByOrderId(String orderId);

    Integer doAddSalerDeal(SalerDeal newDeal);

    Integer doUpdateSalerDeal(SalerDeal newDeal);
}
