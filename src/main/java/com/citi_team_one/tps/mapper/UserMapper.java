package com.citi_team_one.tps.mapper;

import com.citi_team_one.tps.model.User;

import java.util.List;

public interface UserMapper {
    List<User> doFindAll();
    User doFindByName(String name);
    User doFindById(String id);
}