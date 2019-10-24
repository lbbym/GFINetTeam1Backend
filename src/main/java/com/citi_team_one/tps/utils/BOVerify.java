package com.citi_team_one.tps.utils;

import com.citi_team_one.tps.model.SalerDeal;
import com.citi_team_one.tps.model.TraderDeal;

import javax.jms.JMSException;

public class BOVerify {
    public String getBOVerifyMsg(SalerDeal salerDeal, TraderDeal traderDeal) throws JMSException {
        QueueSendMsg qsg = new QueueSendMsg();
        qsg.sendMsg(salerDeal);

        QueueAcceptMsg qam = new QueueAcceptMsg(salerDeal, traderDeal);
        qam.acceptMsg(salerDeal, traderDeal);

        return null;
    }
}
