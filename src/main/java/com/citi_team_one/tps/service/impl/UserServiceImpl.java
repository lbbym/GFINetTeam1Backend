package com.citi_team_one.tps.service.impl;

import com.citi_team_one.tps.mapper.UserMapper;
import com.citi_team_one.tps.model.User;
import com.citi_team_one.tps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper mapper;


    @Override
    public List<User> findAll() {
        return mapper.getAll();
    }
}
