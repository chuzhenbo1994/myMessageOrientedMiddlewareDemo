package com.example.server;

import com.example.server.RabbitMQ.publish.BasicPublisher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RabbitMqText {


    @Autowired
    private BasicPublisher basicPublisher;
    @Test
    public void text1(){
        String  message = "哈哈哈哈  这是一条主要的消息 。";
        try {
            basicPublisher.sentMessage(message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }
}
