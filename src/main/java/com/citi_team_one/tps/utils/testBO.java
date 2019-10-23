package com.citi_team_one.tps.utils;

import com.citi_team_one.tps.model.SalerDeal;
import com.citi_team_one.tps.model.TraderDeal;

import javax.jms.JMSException;

public class testBO {
    public static void main(String[] args) throws JMSException {
        SalerDeal salerDeal = new SalerDeal();
        salerDeal.setId(1);
        salerDeal.setPrice((double)199);

        TraderDeal traderDeal = new TraderDeal();

        BOVerify BOVerify = new BOVerify();
        BOVerify.getBOVerifyMsg(salerDeal,traderDeal);

    }
}
