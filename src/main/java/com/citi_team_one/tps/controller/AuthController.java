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

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;
    private static Logger logger = Logger.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseBean login(@RequestParam("name") String username,
                              @RequestParam("pwd") String password) {
        logger.info(" login: " + username + " " + password);
        User user = userService.findByName(username);
        if (user.getPwd().equals(password)) {
            JSONObject json_result = new JSONObject(true);
            json_result.put("token", JWTUtil.sign(username, password));
            json_result.put("userId", user.getId());
            logger.info(" login successful: " + json_result);
            return new ResponseBean(200, "Login successful.", json_result);
        } else {
            logger.error(" login failed: " + username + " " + password);
            throw new UnauthorizedException();
        }
    }

    @PostMapping("/register")
    public ResponseBean register(User user) {
        logger.info(" register: " + user.getName());
        if (userService.findByName(user.getName()) != null) {
            return new ResponseBean(400, "Already has a name like this!", null);
        }
        else {
            userService.addUser(user);
            return new ResponseBean(200, "Register successful.", null);
        }
    }
}