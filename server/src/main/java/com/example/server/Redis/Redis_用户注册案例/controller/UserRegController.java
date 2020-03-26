package com.example.server.Redis.Redis_用户注册案例.controller;

import com.example.server.Redis.Redis_抢红包案例.reponse.BaseResponse;
import com.example.server.Redis.Redis_抢红包案例.reponse.StatusCode;
import com.example.server.Redis.Redis_用户注册案例.domain.UserReg;
import com.example.server.Redis.Redis_用户注册案例.domain.UserRegDto;
import com.example.server.Redis.Redis_用户注册案例.service.UserRegService;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/log")
@Controller
public class UserRegController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String prefix = "/user/reg";

    @Autowired
    private UserRegService userRegService;

    @RequestMapping(value = prefix + "/submit", method = RequestMethod.GET)
    public BaseResponse reg(UserRegDto userRegDto){
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
      //  userRegService.userRegNoLock(userRegDto);
      //  userRegService.userRegWithLock(userRegDto);
        try {
            userRegService.userRegWithZKLock(userRegDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}
