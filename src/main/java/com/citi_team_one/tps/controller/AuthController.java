package com.citi_team_one.tps.controller;

import com.alibaba.fastjson.JSONObject;
import com.citi_team_one.tps.auth.JWTUtil;
import com.citi_team_one.tps.exception.UnauthorizedException;
import com.citi_team_one.tps.model.ResponseBean;
import com.citi_team_one.tps.model.User;
import com.citi_team_one.tps.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;
    private static Logger logger = Logger.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseBean login(@RequestParam("name") String username,
                              @RequestParam("pwd") String password) {
        logger.info("[LOGIN]" + username + " " + password);
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
        } else {
            userService.addUser(user);
            return new ResponseBean(200, "Register successful.", null);
        }
    }
}