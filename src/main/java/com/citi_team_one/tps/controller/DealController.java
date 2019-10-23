package com.citi_team_one.tps.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citi_team_one.tps.auth.JWTUtil;
import com.citi_team_one.tps.model.*;
import com.citi_team_one.tps.service.CusipUserService;
import com.citi_team_one.tps.service.SalerDealsService;
import com.citi_team_one.tps.service.TraderDealsService;
import com.citi_team_one.tps.utils.DealMatcher;
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

@Api(value = "Deals controller", tags = {"Deals controller"})
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

    @ApiOperation(value = "Get all trader-created deals by trader id", notes = "Called by TW when page first created")
    @RequestMapping(value = "/traderDeal", method = RequestMethod.GET)
    public JSONObject getTraderDeals(@RequestParam("traderId") @ApiParam(value = "UserId: who log in TW", required = true) Integer traderId) {
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

    @ApiOperation(value = "Add a trader-created deal", notes = "Called by TW when click 'Add'")
    @RequestMapping(value = "/traderDeal", method = RequestMethod.POST)
    public TraderDeal addTraderDeal(@RequestParam("productId") @ApiParam(value = "ProductId: same as cusip", required = true) String productId,
                                    @RequestParam("senderId") @ApiParam(value = "UserId: who log in TW", required = true) Integer senderId,
                                    @RequestParam("reciverId") @ApiParam(value = "UserId: always saler's Id", required = true) Integer reciverId,
                                    @RequestParam("volume") @ApiParam(value = "the number of goods", required = true) Integer volume,
                                    @RequestParam("price") @ApiParam(value = "", required = true) Double price) {
        TraderDeal traderDeal = new TraderDeal();
        traderDeal.setStatus(StatusCode.REQUESTED);
        traderDeal.setProductId(productId);
        traderDeal.setTradeSender(senderId);
        traderDeal.setTradeReciver(reciverId);
        traderDeal.setVolume(volume);
        traderDeal.setPrice(price);
        traderDeal.setInterI(senderId);
        traderDeal.setVer(1);
        traderDeal.setInterVNum(1);
        //TODO send it to Matcher and immediately return a deal with StatusCode.PENDING

        SalerDeal salerDeal = DealMatcher.getInstance().getMatchedDeal(traderDeal);
//        if (salerDeal != null && salerDeal.getStatus().equals(StatusCode.TPS_PROCESSED)) {
//            //already TPS_PROCESSED, we send it to BO
//            salerDeal.setStatus(BO ? StatusCode.ACCEPTED : StatusCode.REJECTED);
//            traderDeal.setStatus(BO ? StatusCode.ACCEPTED : StatusCode.REJECTED);
//
//            salerDealsService.updateSalerDeal(salerDeal);
//            traderDealsService.updateTraderDeal(traderDeal);
//        }
        return traderDeal;
    }

    @ApiOperation(value = "Add a saler-created deal", notes = "Called by SW when click 'Add'")
    @RequestMapping(value = "/salerDeal", method = RequestMethod.POST)
    public SalerDeal addSalerDeal(@RequestParam("productId") @ApiParam(value = "ProductId: same as cusip", required = true) String productId,
                                  @RequestParam("senderId") @ApiParam(value = "UserId: who log in SW, this is also a constant value", required = true) Integer senderId,
                                  @RequestParam("reciverId") @ApiParam(value = "UserId: trader's id", required = true) Integer reciverId,
                                  @RequestParam("volume") @ApiParam(value = "the number of goods", required = true) Integer volume,
                                  @RequestParam("price") @ApiParam(value = "", required = true) Double price) {
        SalerDeal salerDeal = new SalerDeal();
        salerDeal.setStatus(StatusCode.REQUESTED);
        salerDeal.setProductId(productId);
        salerDeal.setTradeSender(senderId);
        salerDeal.setTradeReciver(reciverId);
        salerDeal.setVolume(volume);
        salerDeal.setPrice(price);
        salerDeal.setInterI(senderId);
        salerDeal.setVer(1);
        salerDeal.setInterVNum(1);
        //TODO send it to Matcher and immediately return a deal with StatusCode.PENDING

        TraderDeal traderDeal = DealMatcher.getInstance().getMatchedDeal(salerDeal);
        if (traderDeal != null && traderDeal.getStatus().equals(StatusCode.TPS_PROCESSED)) {
            //already TPS_PROCESSED, we send it to BO
            salerDeal.setStatus(BO ? StatusCode.ACCEPTED : StatusCode.REJECTED);
            traderDeal.setStatus(BO ? StatusCode.ACCEPTED : StatusCode.REJECTED);

            salerDealsService.updateSalerDeal(salerDeal);
            traderDealsService.updateTraderDeal(traderDeal);
        }
        return salerDeal;
    }

    @ApiOperation(value = "Update a trader-created deal with new price", notes = "Called by TW when click 'Edit'")
    @RequestMapping(value = "/traderDeal", method = RequestMethod.PUT)
    public TraderDeal updateTraderDeal(@ApiParam(value = "id of the trade where you click 'Edit'", required = true) Integer traderDealId,
                                       @ApiParam(value = "new value you input", required = true) Double newPrice) {
        TraderDeal traderDeal = traderDealsService.findById(traderDealId);
        traderDeal.setPrice(newPrice);
        traderDealsService.updateTraderDeal(traderDeal);
        //TODO send it to Matcher
        return traderDeal;
    }

    @ApiOperation(value = "Update a saler-created deal with its relative trader-created deal's price value", notes = "Called by TW when click 'Merge'")
    @RequestMapping(value = "/merge", method = RequestMethod.PUT)
    public SalerDeal merge(@ApiParam(value = "id of the trade where you click 'Merge'. and this is a salerDeal", required = true) Integer salerDealId,
                           @ApiParam(value = "price of this salerDeal's assicoted deal, which is a tradeDeal", required = true) Double traderDealPrice) {
        SalerDeal salerDeal = salerDealsService.findById(salerDealId);
        salerDeal.setPrice(traderDealPrice);
        salerDealsService.updateSalerDeal(salerDeal);
        //TODO send it to Matcher
        return salerDeal;
    }

    @ApiOperation(value = "Add a cusip-user record", notes = "Called by TW when click 'Add Cusip'")
    @RequestMapping(value = "/cusipUser", method = RequestMethod.POST)
    public CusipUser addCusipForUser(@ApiParam(value = "UserId: who log in TW", required = true) Integer traderId,
                                     @ApiParam(value = "", required = true) String productId) {
        CusipUser cusipUser = new CusipUser();
        cusipUser.setUserId(traderId);
        cusipUser.setProductId(productId);
        cusipUserService.addCusipUser(cusipUser);
        return cusipUser;
    }

    @ApiOperation(value = "Get all cusips this user owned", notes = "Called by TW when page first created")
    @RequestMapping(value = "/cusipUser", method = RequestMethod.GET)
    public JSONObject getCusips(@RequestParam("traderId") @ApiParam(value = "UserId: who log in TW", required = true) Integer traderId) {
        JSONObject json_result = new JSONObject(true);
        json_result.put("myCusips", cusipUserService.findProductsByTraderId(traderId));
        return json_result;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }
}