package com.citi_team_one.tps.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citi_team_one.tps.auth.JWTUtil;
import com.citi_team_one.tps.model.*;
import com.citi_team_one.tps.service.CusipUserService;
import com.citi_team_one.tps.service.ProductService;
import com.citi_team_one.tps.service.SalerDealsService;
import com.citi_team_one.tps.service.TraderDealsService;
import com.citi_team_one.tps.utils.DealMatcher;
import com.citi_team_one.tps.utils.StatusUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DealController {

    private boolean BO = true;
    @Autowired
    private TraderDealsService traderDealsService;
    @Autowired
    private SalerDealsService salerDealsService;
    @Autowired
    private CusipUserService cusipUserService;
    @Autowired
    private ProductService productService;

    @ApiOperation(
            value = "获取该用户下的所有Cusip, 以及该Cusip下的所有traderDeal",
            notes = "在用户刚刚登入TW时调用该api",
            tags = "获取traderDeal")
    @RequestMapping(value = "/traderDeal", method = RequestMethod.GET)
    public JSONObject getTraderDeals(@RequestParam("traderId") @ApiParam(value = "UserId: 登入TW的用户的ID", required = true, example = "1") Integer traderId) {
        JSONObject json_result = new JSONObject(true);
        JSONArray cusips = new JSONArray();

        List<Product> products = cusipUserService.findProductsByTraderId(traderId);
        for (Product p : products) {
            JSONObject productAndItsDeals = new JSONObject(true);
            productAndItsDeals.put("product", p);
            List<TraderDeal> traderDealsBelongToThisProductAndUser = new ArrayList<>();
            List<TraderDeal> traderDealsBelongToThisUser = traderDealsService.findAllBySenderId(traderId);
            for (TraderDeal traderDeal : traderDealsBelongToThisUser) {
                if (traderDeal.getProductId().equals(p.getCusip())) {
                    traderDealsBelongToThisProductAndUser.add(traderDeal);
                }
            }
            //Now traderDealsBelongToThisProductAndUser filled
            JSONArray deals = new JSONArray();
            for (TraderDeal traderDeal : traderDealsBelongToThisProductAndUser) {
                JSONObject dealPair = new JSONObject(true);
                dealPair.put("tradeDeal", traderDeal);
                String orderId = traderDeal.getOrderId();
                if (orderId == null) {
                    dealPair.put("salerDeal", null);
                    productAndItsDeals.put("dealPair", dealPair);
                    continue;
                }
                SalerDeal salerDeal = salerDealsService.findByOrderId(orderId);
                dealPair.put("salerDeal", salerDeal);
                deals.add(dealPair);
            }
            productAndItsDeals.put("deals", deals);
            cusips.add(productAndItsDeals);
        }
        json_result.put("cusips", cusips);
        return json_result;
    }

    @ApiOperation(
            value = "添加一条traderDeal",
            notes = "在用户登入TW后， 点击Add trader时调用",
            tags = "添加traderDeal")
    @RequestMapping(value = "/traderDeal", method = RequestMethod.POST)
    public TraderDeal addTraderDeal(@RequestParam("productId") @ApiParam(value = "ProductId: 就是当前的Cusip", required = true, example = "1") String productId,
                                    @RequestParam("senderId") @ApiParam(value = "UserId: 谁发送的这个trade, 一般是登录TW的那个人", required = true, example = "1") Integer senderId,
                                    @RequestParam("reciverId") @ApiParam(value = "UserId: 谁接受这个trade, 一般是唯一的那一个Saler", required = true, example = "1") Integer reciverId,
                                    @RequestParam("volume") @ApiParam(value = "商品数量", required = true, example = "1") Integer volume,
                                    @RequestParam("price") @ApiParam(value = "", required = true, example = "1") Double price) {
        TraderDeal traderDeal = new TraderDeal();
        traderDeal.setStatus(StatusCode.REQUESTED);
        traderDeal.setProductId(productId);
        traderDeal.setTradeSender(senderId);
        traderDeal.setTradeReciver(reciverId);
        traderDeal.setVolume(volume);
        traderDeal.setPrice(price);
        traderDeal.setNotionalPrincipal(volume * price);
        traderDeal.setInterI(senderId);
        traderDeal.setVer(StatusUtil.stastr2int(traderDeal.getStatus()));
        traderDeal.setInterVNum(1);
        traderDeal.setTradeOrigSys("TW");
        traderDeal.setInterOrigSys("TW");
        try {
            DealMatcher.getInstance().isMatch(traderDeal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return traderDeal;
    }

    @ApiOperation(
            value = "添加一条salerDeal",
            notes = "在用户登入SW后， 点击Add trader时调用",
            tags = "添加salerDeal")
    @RequestMapping(value = "/salerDeal", method = RequestMethod.POST)
    public SalerDeal addSalerDeal(@RequestParam("productId") @ApiParam(value = "ProductId: 先调用GetProducts获取所有的Products， 然后选一个填到这里", required = true, example = "1") String productId,
                                  @RequestParam("senderId") @ApiParam(value = "UserId: 谁发送的这个trade, 是登录SW的那个唯一的人", required = true, example = "1") Integer senderId,
                                  @RequestParam("receiverId") @ApiParam(value = "UserId:  谁接受这个trade, 是某一个trader", required = true, example = "1") Integer reciverId,
                                  @RequestParam("volume") @ApiParam(value = "商品数量", required = true, example = "1") Integer volume,
                                  @RequestParam("price") @ApiParam(value = "价格", required = true, example = "1") Double price) {
        SalerDeal salerDeal = new SalerDeal();
        salerDeal.setStatus(StatusCode.REQUESTED);
        salerDeal.setProductId(productId);
        salerDeal.setTradeSender(senderId);
        salerDeal.setTradeReciver(reciverId);
        salerDeal.setVolume(volume);
        salerDeal.setPrice(price);
        salerDeal.setNotionalPrincipal(volume * price);
        salerDeal.setInterI(senderId);
        salerDeal.setVer(StatusUtil.stastr2int(salerDeal.getStatus()));
        salerDeal.setInterVNum(1);
        salerDeal.setTradeOrigSys("SW");
        salerDeal.setInterOrigSys("SW");
        try {
            DealMatcher.getInstance().isMatch(salerDeal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salerDeal;
    }

    @ApiOperation(
            value = "用新输入的价格去更新一条tradeDeal",
            notes = "在TW里点了'Edit'之后调用",
            tags = "更新traderDeal")
    @RequestMapping(value = "/traderDeal", method = RequestMethod.PUT)
    public TraderDeal updateTraderDeal(@RequestParam("traderDealId") @ApiParam(value = "要修改的traderDeal的Id", required = true, example = "1") Integer traderDealId,
                                       @RequestParam("newPrice") @ApiParam(value = "新的价格", required = true, example = "1") Double newPrice) {
        TraderDeal traderDeal = traderDealsService.findById(traderDealId);
        traderDeal.setPrice(newPrice);

        traderDeal.setInterI(traderDeal.getTradeSender());
        traderDeal.setInterOrigSys("TW");
        traderDeal.setInterVNum(traderDeal.getInterVNum()+1);
        traderDealsService.updateTraderDeal(traderDeal);
        try {
            DealMatcher.getInstance().isMatch(traderDeal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return traderDeal;
    }

    @ApiOperation(
            value = "用同属于一份订单的traderDeald的价格去更新一条salerDeal",
            notes = "在TW里点了'Merge'之后调用",
            tags = "合并/同步traderDeal和salerDeal")
    @RequestMapping(value = "/merge", method = RequestMethod.PUT)
    public SalerDeal merge(@RequestParam("salerDealId") @ApiParam(value = "要修改的salerDeal的Id", required = true, example = "1") Integer salerDealId,
                           @RequestParam("traderDealPrice") @ApiParam(value = "同属于一份订单的traderDeald的价格", required = true, example = "1") Double traderDealPrice) {
        SalerDeal salerDeal = salerDealsService.findById(salerDealId);
        salerDeal.setPrice(traderDealPrice);

        salerDeal.setInterI(salerDeal.getTradeReciver());
        salerDeal.setInterOrigSys("TW");
        salerDeal.setInterVNum(salerDeal.getInterVNum()+1);
        salerDealsService.updateSalerDeal(salerDeal);
        try {
            DealMatcher.getInstance().isMatch(salerDeal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salerDeal;
    }

    @ApiOperation(
            value = "给当前用户添加一个Cusip",
            notes = "在用户登入TW后， 点击'Add Cusip'时调用",
            tags = "添加CusipUser关系")
    @RequestMapping(value = "/cusipUser", method = RequestMethod.POST)
    public CusipUser addCusipForUser(@RequestParam("traderId") @ApiParam(value = "UserId: 登入TW的用户的ID", required = true, example = "1") Integer traderId,
                                     @RequestParam("productId") @ApiParam(value = "要添加的Cusip的ID", required = true, example = "1") String productId) {
        CusipUser cusipUser = new CusipUser();
        cusipUser.setUserId(traderId);
        cusipUser.setProductId(productId);
        cusipUserService.addCusipUser(cusipUser);
        return cusipUser;
    }

    @ApiOperation(
            value = "获取所有商品信息",
            notes = "在用户登入TW时就要调用了",
            tags = "获取所有商品信息")
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }
}