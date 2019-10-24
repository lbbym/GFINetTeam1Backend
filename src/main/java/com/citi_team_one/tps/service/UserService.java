package com.citi_team_one.tps.service;

import com.citi_team_one.tps.model.User;

import java.util.List;

public interface UserService {
    public List<User> findAll();

    public User findByName(String name);

    public User findById(Integer id);

    public User addUser(User user);
}
