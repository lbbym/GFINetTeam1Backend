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
    private UserMapper userMapper;


    @Override
    public List<User> findAll() {
        return userMapper.doFindAll();
    }

    @Override
    public User findByName(String name) {
       return userMapper.doFindByName(name);
    }

    @Override
    public User findById(Integer id) {
        return userMapper.doFindById(id);
    }

    @Override
    public User addUser(User user){
        user.setId(null);
        userMapper.doAddUser(user);
        return user;
    }
}
