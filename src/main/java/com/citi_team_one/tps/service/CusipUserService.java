package com.citi_team_one.tps.service;

import com.citi_team_one.tps.model.CusipUser;
import com.citi_team_one.tps.model.Product;

import java.util.List;

public interface CusipUserService {
    public CusipUser addCusipUser(CusipUser cusipUser);

    public List<Product> findProductsByTraderId(Integer traderId);
}
