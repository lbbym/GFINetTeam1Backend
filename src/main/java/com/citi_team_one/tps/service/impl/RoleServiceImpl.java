package com.citi_team_one.tps.service.impl;

import com.citi_team_one.tps.mapper.RoleMapper;
import com.citi_team_one.tps.model.Role;
import com.citi_team_one.tps.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role findById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }
}
