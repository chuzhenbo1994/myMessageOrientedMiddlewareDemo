package com.example.server;

import com.example.server.RabbitDeadLetteQueue.entity.DeadInfo;
import com.example.server.RabbitDeadLetteQueue.publisher.DeadPublisher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RabbitDeadQueueText {

    @Autowired
    private DeadPublisher deadPublisher;
    @Test
    public void text1(){
       deadPublisher.deadSendMsg(new DeadInfo(111111111,"這是一個死信隊列"));
    }
}
