package com.citi_team_one.tps.controller;


import com.alibaba.fastjson.JSONObject;
import com.citi_team_one.tps.auth.JWTUtil;
import com.citi_team_one.tps.exception.UnauthorizedException;
import com.citi_team_one.tps.mapper.UserMapper;
import com.citi_team_one.tps.model.ResponseBean;
import com.citi_team_one.tps.model.User;
import com.citi_team_one.tps.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @RequiresAuthentication
    public List<User> getAllUsers() {
        List<User> users = userService.findAll();
        return users;
    }

    @GetMapping("/login")
    public ResponseBean login(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
        System.out.println(username + " " + password);
        User user = userService.findByName(username);
        if (user.getPwd().equals(password)) {
            JSONObject json_result = new JSONObject(true);
            json_result.put("token", JWTUtil.sign(username, password));
            json_result.put("uid",user.getId());
            System.out.println(json_result);
            return new ResponseBean(200, "Login successful.", json_result);
        } else {
            throw new UnauthorizedException();
        }
    }
}