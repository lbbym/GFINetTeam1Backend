package com.citi_team_one.tps.utils;

import com.citi_team_one.tps.model.SalerDeal;
import com.citi_team_one.tps.model.StatusCode;
import com.citi_team_one.tps.model.TraderDeal;
import com.citi_team_one.tps.service.SalerDealsService;
import com.citi_team_one.tps.service.TraderDealsService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.*;

public class QueueAcceptMsg implements MessageListener {
    @Autowired
    private SalerDealsService salerDealsService;
    @Autowired
    private TraderDealsService traderDealsService;

    public static final String user="";
    public static final String pwd="";
    public static final String url = "tcp://47.100.138.62:61616";
    public static final String name = "queue.msgTxt";
    boolean ifReply=false;
    String stringRes=null;

    ActiveMQConnectionFactory connectionfactory =null;
    Connection connection=null;
    Session session=null;
    SalerDeal salerDeal;
    TraderDeal traderDeal;

    public void acceptMsg(SalerDeal salerDeal, TraderDeal traderDeal) throws JMSException {
        this.salerDeal = salerDeal;
        this.traderDeal = traderDeal;
        if(connectionfactory==null){
            connectionfactory = new ActiveMQConnectionFactory(url);
//            connectionfactory = new ActiveMQConnectionFactory(user,pwd,url);
            connectionfactory.setTrustAllPackages(true);
        }
        if(connection==null){
            connection = connectionfactory.createConnection();
            connection.start();
        }
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = new ActiveMQQueue(name);
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new QueueAcceptMsg());
    }

    @Override
    public void onMessage(Message message) {
        if(message instanceof TextMessage){
            TextMessage text = (TextMessage) message;
            try {
                stringRes = text.getText();
                System.out.println("accepted text message:"+text.getText());
                //salerDeal.setStatus();
                //update the DB
                if(text.getText().equals("accept")){
                    salerDeal.setStatus(StatusCode.ACCEPTED);
                    traderDeal.setStatus(StatusCode.ACCEPTED);
                    salerDealsService.updateSalerDeal(salerDeal);
                    traderDealsService.updateTraderDeal(traderDeal);
                } else if(text.getText().equals("reject")){
                    salerDeal.setStatus(StatusCode.REJECTED);
                    traderDeal.setStatus(StatusCode.REJECTED);
                    salerDealsService.updateSalerDeal(salerDeal);
                    traderDealsService.updateTraderDeal(traderDeal);
                }

            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
