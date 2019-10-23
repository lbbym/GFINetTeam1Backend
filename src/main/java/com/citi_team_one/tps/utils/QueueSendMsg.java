package com.citi_team_one.tps.utils;

import com.citi_team_one.tps.model.SalerDeal;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;
import java.io.Serializable;

public class QueueSendMsg {
    public static final String user="";
    public static final String pwd="";
    public static final String url = "tcp://tcp://47.100.138.62:61616";
    public static final String name = "queue.msgObj";
    public void sendMsg(SalerDeal salerDeal) throws JMSException {
        ConnectionFactory connectionfactory = new ActiveMQConnectionFactory(url);
//        connectionfactory = new ActiveMQConnectionFactory(user,pwd,url);
        Connection connection = connectionfactory.createConnection();
        Session session =connection.createSession(false,Session.AUTO_ACKNOWLEDGE );
        QueueSendMsg qs = new QueueSendMsg();

        qs.sendObj(session, salerDeal, name);
        session.close();
        connection.close();
    }

    private void sendObj(Session session, Object obj, String name) throws JMSException {
        Destination queue = new ActiveMQQueue(name);
        MessageProducer msgProducer = session.createProducer(queue);
        ObjectMessage objMsg=session.createObjectMessage((Serializable) obj);
        MessageProducer msgPorducer =session.createProducer(queue);
        msgPorducer.send(objMsg);
        System.out.println("send message finished");
    }


}
