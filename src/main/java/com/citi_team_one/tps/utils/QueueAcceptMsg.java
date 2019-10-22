package com.citi_team_one.tps.utils;

import com.citi_team_one.tps.model.SalerDeal;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;

public class QueueAcceptMsg implements MessageListener {
    public static final String user="";
    public static final String pwd="";
    public static final String url = "tcp://localhost:61616";
    public static final String name = "queue.msgTxt";
    boolean ifReply=false;
    String stringRes=null;

    ActiveMQConnectionFactory connectionfactory =null;
    Connection connection=null;
    Session session=null;
    SalerDeal salerDeal;

    public void acceptMsg(SalerDeal salerDeal) throws JMSException {
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

            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
