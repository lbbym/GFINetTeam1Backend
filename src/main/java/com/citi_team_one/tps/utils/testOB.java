package com.citi_team_one.tps.utils;

import com.citi_team_one.tps.model.SalerDeal;

import javax.jms.JMSException;

public class testOB {
    public static void main(String[] args) throws JMSException {
        SalerDeal salerDeal = new SalerDeal();
        salerDeal.setId(1);
        salerDeal.setPrice((double)199);

        OBVerify obVerify = new OBVerify();
        obVerify.getOBVerifyMsg(salerDeal);

    }
}
