package com.citi_team_one.tps.service.impl;

import com.citi_team_one.tps.mapper.SalerDealMapper;
import com.citi_team_one.tps.model.SalerDeal;
import com.citi_team_one.tps.service.SalerDealsService;
import com.citi_team_one.tps.utils.StatusUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalerDealServiceImpl implements SalerDealsService {
    @Autowired
    private SalerDealMapper salerDealMapper;

    @Override
    public SalerDeal findById(Integer id) {
        return salerDealMapper.doFindByTxnI(id);
    }

    @Override
    public SalerDeal findByOrderId(String orderId) {
        return salerDealMapper.doFindByOrderId(orderId);
    }

    @Override
    public List<SalerDeal> findAllInPages(Integer pageNum, Integer perPage) {
        PageHelper.startPage(pageNum, perPage);
        return salerDealMapper.doFindAll();
    }

    @Override
    public SalerDeal addSalerDeal(SalerDeal newDeal) {
        newDeal.setTxnI(null);
        newDeal.setVer(StatusUtil.stastr2int(newDeal.getStatus()) + newDeal.getInterVNum());

        salerDealMapper.doAddSalerDeal(newDeal);
        return newDeal;
    }

    @Override
    public SalerDeal updateSalerDeal(SalerDeal updatedDeal) {
        updatedDeal.setNotionalPrincipal(updatedDeal.getVolume() * updatedDeal.getPrice());
        updatedDeal.setVer(StatusUtil.stastr2int(updatedDeal.getStatus()) + updatedDeal.getInterVNum());
        salerDealMapper.doUpdateSalerDeal(updatedDeal);
        return updatedDeal;
    }
}
