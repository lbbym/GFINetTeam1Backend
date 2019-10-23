package com.citi_team_one.tps.mapper;

import com.citi_team_one.tps.model.CusipUser;

public interface CusipUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CusipUser record);

    int insertSelective(CusipUser record);

    CusipUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CusipUser record);

    int updateByPrimaryKey(CusipUser record);
}