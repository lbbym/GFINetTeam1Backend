package com.citi_team_one.tps.controller;


import com.citi_team_one.tps.mapper.UserMapper;
import com.citi_team_one.tps.model.User;
import com.citi_team_one.tps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @RequestMapping("/todos")
    public List<Integer> getAllTodos() {
        List<Integer> tmp = new ArrayList<>();
        tmp.add(1);
        tmp.add(2432);
        return tmp;
    }

    @RequestMapping("/users")
    public List<User> getAllUsers() {
        List<User> users = userService.findAll();
        return users;
    }
}