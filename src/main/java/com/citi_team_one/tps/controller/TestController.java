package com.citi_team_one.tps.controller;


import com.alibaba.fastjson.JSONObject;
import com.citi_team_one.tps.auth.JWTUtil;
import com.citi_team_one.tps.exception.UnauthorizedException;
import com.citi_team_one.tps.model.ResponseBean;
import com.citi_team_one.tps.model.SalerDeal;
import com.citi_team_one.tps.model.TraderDeal;
import com.citi_team_one.tps.model.User;
import com.citi_team_one.tps.service.SalerDealsService;
import com.citi_team_one.tps.service.TraderDealsService;
import com.citi_team_one.tps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @Autowired
    private TraderDealsService traderDealsService;

    @Autowired
    private SalerDealsService salerDealsService;

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

    @RequestMapping("/traderDeals")
    public List<TraderDeal> getAllTraderDeals() {
        List<TraderDeal> traderDeals = traderDealsService.findAllInPages(1,10);
        return traderDeals;
    }

    @RequestMapping("/salerDeals")
    public List<SalerDeal> getAllSalerDeals() {
        List<SalerDeal> salerDeals = salerDealsService.findAllInPages(1,10);
        return salerDeals;
    }
}