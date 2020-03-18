package com.example.server;

import com.example.server.RabbitMQ_A.publish.BasicPublisher;
import com.example.server.RabbitMQ_B.domain.Person;
import com.example.server.RabbitMQ_B.publish.BasicPublisher_obj;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RabbitMqText {


    @Autowired
    private BasicPublisher basicPublisher;
    @Autowired
    private BasicPublisher_obj basicPublisherObj;

    @Test
    public void text1(){
        String  message = "哈哈哈哈  这是一条主要的消息 。";
        try {
            basicPublisher.sentMessage(message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void text2(){
        try {
            basicPublisherObj.sentMessage(new Person(1,"哈哈哈","明白人"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
