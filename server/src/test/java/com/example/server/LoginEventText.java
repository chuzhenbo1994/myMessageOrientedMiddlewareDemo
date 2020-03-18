package com.example.server;

import com.example.server.LginEvent.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class LoginEventText {

    @Autowired
    private Producer producer;
    @Test
    public void  text1(){
        producer.setPublisher();
    }
}
