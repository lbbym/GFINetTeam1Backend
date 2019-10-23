package com.citi_team_one.tps.service.impl;

import com.citi_team_one.tps.mapper.CusipUserMapper;
import com.citi_team_one.tps.model.CusipUser;
import com.citi_team_one.tps.model.Product;
import com.citi_team_one.tps.service.CusipUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CusipUserServiceImpl implements CusipUserService {
    @Autowired
    private CusipUserMapper cusipUserMapper;
    @Override
    public CusipUser addCusipUser(CusipUser cusipUser) {
        cusipUserMapper.insert(cusipUser);
        return cusipUser;
    }

    @Override
    public List<Product> findProductsByTraderId(Integer traderId) {
        return cusipUserMapper.doFindProductsByTraderId(traderId);
    }
}
