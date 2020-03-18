package com.example.server.Redis.Redis_缓存穿透.controller;

import com.example.server.Redis.Redis_缓存穿透.server.CachePassService;
import com.example.server.Redis.Redis_缓存穿透.repository.ItemRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;

@Controller
@RequestMapping("/a")
public class ItemController {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ItemRepository repository;

    @Autowired
    private CachePassService service;
    /**
     * 测试redis缓存穿透的一个类
     */
    @RequestMapping("/b")
    public void textRedis(String itemCode){
        Map map = new HashMap();
        try {
            map.put("data",service.getItemInfo(itemCode));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

}
