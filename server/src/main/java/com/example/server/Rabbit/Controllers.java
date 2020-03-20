package com.example.server.Rabbit;

import com.example.server.Rabbit.AutoACK.publish.KnowledgePublisher;
import com.example.server.Rabbit.DirectExchange.publish.DirectModelPublisher;
import com.example.server.Rabbit.domain.EventInfo;
import com.example.server.Rabbit.domain.KnowledgeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

@RequestMapping("/a")
@Controller
public class Controllers {
    @Autowired
    KnowledgePublisher knowledgePublisher;
    @Autowired
    private DirectModelPublisher directModelPublisher;

    @RequestMapping("/d")
    @ResponseBody
    public void text1(){
        try {
            knowledgePublisher.sentMessage(new KnowledgeInfo(1,"这是一个自动的确认消费","666"));
          //  directModelPublisher.sendMegDirectOne(new EventInfo(222,"直接发送的一个交换机","name","abc"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
