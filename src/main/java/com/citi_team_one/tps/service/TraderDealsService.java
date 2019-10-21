package com.citi_team_one.tps.service;

import java.util.List;

public interface TraderDealsService {
    public List<TraderDeal> findAllInPages(Integer pageNum, Integer perPage);
    public TraderDeal addTraderDeal(TraderDeal newDeal);
    public TraderDeal updateTraderDeal(TraderDeal updatedDeal);
}
