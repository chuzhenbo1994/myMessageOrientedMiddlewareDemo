package com.example.server;

import com.example.server.Rabbit.AutoACK.publish.KnowledgePublisher;
import com.example.server.Rabbit.domain.KnowledgeInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RabbitACKText {

    @Autowired
    KnowledgePublisher knowledgePublisher;
    @Test
    public void text1(){

        try {
          knowledgePublisher.sentMessage(new KnowledgeInfo(1,"这是一个自动的确认消费","666"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
