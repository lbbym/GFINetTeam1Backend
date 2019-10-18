package com.citi_team_one.tps.mapper;

import com.citi_team_one.tps.model.User;

import java.util.List;

public interface UserMapper {

    List<User> getAll();

    User getOne(String id);
//
//    void insert(User user);
//
//    void update(User user);
//
//    void delete(String id);

}