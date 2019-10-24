package com.citi_team_one.tps.utils;

import com.citi_team_one.tps.model.SalerDeal;
import com.citi_team_one.tps.model.StatusCode;
import com.citi_team_one.tps.model.TraderDeal;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;

public class QueueAcceptMsg implements MessageListener {
    public static final String user = "";
    public static final String pwd = "";
            public static final String url = "tcp://172.17.12.252:61616";
//    public static final String url = "tcp://127.0.0.1:61616";

    public static final String name = "queue.msgTxt";
    boolean ifReply = false;
    String stringRes = null;

    ActiveMQConnectionFactory connectionfactory = null;
    Connection connection = null;
    Session session = null;
    SalerDeal salerDeal;
    TraderDeal traderDeal;

    public QueueAcceptMsg(SalerDeal salerDeal, TraderDeal traderDeal) {
        this.salerDeal = salerDeal;
        this.traderDeal = traderDeal;
    }

    public void acceptMsg(SalerDeal salerDeal, TraderDeal traderDeal) throws JMSException {
        if (connectionfactory == null) {
            connectionfactory = new ActiveMQConnectionFactory(url);
            connectionfactory.setTrustAllPackages(true);
        }
        if (connection == null) {
            connection = connectionfactory.createConnection();
            connection.start();
        }
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = new ActiveMQQueue(name);
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new QueueAcceptMsg(this.salerDeal, this.traderDeal));
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage text = (TextMessage) message;
            try {
                stringRes = text.getText();
                System.out.println("accepted text message:" + text.getText());
                //update the DB
                if (text.getText().substring(0, 6).equals("accept")) {
                    salerDeal.setStatus(StatusCode.ACCEPTED);
                    traderDeal.setStatus(StatusCode.ACCEPTED);
                    ServiceUtil.serviceUtil.salerDealsService.updateSalerDeal(salerDeal);
                    ServiceUtil.serviceUtil.traderDealsService.updateTraderDeal(traderDeal);
                } else if (text.getText().substring(0, 6).equals("reject")) {
                    salerDeal.setStatus(StatusCode.REJECTED);
                    traderDeal.setStatus(StatusCode.REJECTED);
                    if (text.getText().substring(9).equals("100")) {
                        salerDeal.setRejectCode(100);
                        salerDeal.setRejectReason("STALE_DATA");
                    }
                    if (text.getText().substring(9).equals("200")) {
                        salerDeal.setRejectCode(200);
                        salerDeal.setRejectReason("INVALID_REQUEST");
                    }
                    ServiceUtil.serviceUtil.salerDealsService.updateSalerDeal(salerDeal);
                    ServiceUtil.serviceUtil.traderDealsService.updateTraderDeal(traderDeal);
                }

            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
