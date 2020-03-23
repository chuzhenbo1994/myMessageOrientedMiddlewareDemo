package com.example.server;

import com.example.server.Rabbit.RabbitMQ_threeExchangeAndIntroduction.AutoACK.publish.KnowledgePublisher;
import com.example.server.Rabbit.RabbitMQ_threeExchangeAndIntroduction.ManualACK.publish.KnowledgeManualPublisher;
import com.example.server.Rabbit.RabbitMQ_threeExchangeAndIntroduction.domain.KnowledgeInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RabbitACKText {

    @Autowired
    KnowledgePublisher knowledgePublisher;
    @Autowired
    KnowledgeManualPublisher  manualPublisher;
    @Test
    public void text1(){

        try {
          manualPublisher.sentAutoMessage(new KnowledgeInfo(111111,"手动确认信息","6666"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
