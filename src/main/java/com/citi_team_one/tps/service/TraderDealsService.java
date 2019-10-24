package com.citi_team_one.tps.service;

import com.citi_team_one.tps.model.TraderDeal;

import java.util.List;

public interface TraderDealsService {
    public TraderDeal findById(Integer id);

    public List<TraderDeal> findAllBySenderId(Integer id);

    public List<TraderDeal> findAllInPages(Integer pageNum, Integer perPage);

    public TraderDeal addTraderDeal(TraderDeal newDeal);

    public TraderDeal updateTraderDeal(TraderDeal updatedDeal);
}
