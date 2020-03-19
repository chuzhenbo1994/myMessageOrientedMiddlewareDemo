package com.example.server;

import com.example.server.Rabbit.DirectExchange.publish.DirectModelPublisher;
import com.example.server.Rabbit.domain.EventInfo;
import com.example.server.Rabbit.FanoutExchange.publish.ModelPublisher;
import com.example.server.Rabbit.RabbitMQ_A.publish.BasicPublisher;
import com.example.server.Rabbit.domain.Person;
import com.example.server.Rabbit.RabbitMQ_B.publish.BasicPublisher_obj;
import com.example.server.Rabbit.TopicExchange.publish.TopicPublish;
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
    @Autowired
    private ModelPublisher modelPublisher;
    @Autowired
    private DirectModelPublisher directModelPublisher;

    @Autowired
    private TopicPublish topicPublish;
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

    /**\
     *
     */
    @Test
    public void text3(){
        try {
            modelPublisher.sendMeg(new EventInfo(1,"基于fanoutExchange的model","哈哈哈","desc"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void text4(){
        try {
            directModelPublisher.sendMegDirectOne(new EventInfo(1,"基于fanoutExchange的model","哈哈哈","desc"));
         //   directModelPublisher.sendMegDirectTwo(new EventInfo(2,"基于fanoutExchange的model","哈哈哈","desc"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void text5(){
        try {
          topicPublish.sentMessageTp("你好吗，我很好1111","local.middleware.mq.topic.routing.java.one");
          //  topicPublish.sentMessageTp("你好吗，我很好2222","local.middleware.mq.topic.routing.php.python.one");
          //  topicPublish.sentMessageTp("你好吗，我很好3333","local.middleware.mq.topic.routing.one");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
