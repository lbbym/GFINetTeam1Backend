package com.citi_team_one.tps.service.impl;

import com.citi_team_one.tps.mapper.ProductMapper;
import com.citi_team_one.tps.model.Product;
import com.citi_team_one.tps.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Override
    public List<Product> findAll() {
        return productMapper.doFindAll();
    }
}
