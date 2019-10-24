package com.citi_team_one.tps.mapper;
import com.citi_team_one.tps.model.Product;

import java.util.List;

public interface ProductMapper {

    List<Product> doFindAll();
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
}