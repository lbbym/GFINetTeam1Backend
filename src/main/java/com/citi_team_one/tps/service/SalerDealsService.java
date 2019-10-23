package com.citi_team_one.tps.service;

import com.citi_team_one.tps.model.SalerDeal;
import com.citi_team_one.tps.model.User;

import java.util.List;

public interface SalerDealsService {
    public SalerDeal findById(Integer id);

    public SalerDeal findByOrderId(String orderId);

    public List<SalerDeal> findAllInPages(Integer pageNum, Integer perPage);

    public SalerDeal addSalerDeal(SalerDeal newDeal);

    public SalerDeal updateSalerDeal(SalerDeal updatedDeal);
}
