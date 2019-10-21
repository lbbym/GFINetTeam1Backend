package com.citi_team_one.tps.service.impl;

import com.citi_team_one.tps.service.SalerDealsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalerDealsServiceImpl implements SalerDealsService {
    @Autowired
    private SalerDealsMapper salerDealsMapper;


    @Override
    public List<SalerDeal> findAllInPages(Integer pageNum, Integer perPage) {
        return salerDealsMapper.doFindAllInPages(pageNum, perPage);
    }

    @Override
    public SalerDeal addSalerDeal(SalerDeal newDeal) {
        return salerDealsMapper.doAddSalerDeal(newDeal);
    }

    @Override
    public SalerDeal updateSalerDeal(SalerDeal updatedDeal) {
        return salerDealsMapper.doUpdateSalerDeal(updatedDeal);
    }
}
