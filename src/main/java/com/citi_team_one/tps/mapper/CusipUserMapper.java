package com.citi_team_one.tps.mapper;

import com.citi_team_one.tps.model.CusipUser;
import com.citi_team_one.tps.model.Product;
import java.util.List;
public interface CusipUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CusipUser record);

    int insertSelective(CusipUser record);

    CusipUser selectByPrimaryKey(Integer id);

    List<Product> doFindProductsByTraderId(Integer user_id);

    int updateByPrimaryKeySelective(CusipUser record);

    int updateByPrimaryKey(CusipUser record);
}