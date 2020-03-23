package com.example.server.Rabbit.RabbitCase_loginLog.controller;

import com.example.server.Rabbit.RabbitCase_loginLog.domain.UserLogDto;
import com.example.server.Rabbit.RabbitCase_loginLog.service.UserService;
import com.example.server.Redis.Redis_抢红包案例.reponse.BaseResponse;
import com.example.server.Redis.Redis_抢红包案例.reponse.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/middleware")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String prefix = "/user";

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = prefix + "/login")
    public BaseResponse login(@RequestBody @Validated UserLogDto userLogDto, BindingResult result) {
        if (result.hasErrors()) {
            return new BaseResponse(StatusCode.SUCCESS);
        }
        BaseResponse baseResponse = new BaseResponse(StatusCode.SUCCESS);

        try {
            Boolean login = userService.login(userLogDto);
            if (login) {
                baseResponse = new BaseResponse(StatusCode.SUCCESS, "登录成功");
            } else {
                baseResponse = new BaseResponse(StatusCode.FAIL, "登录失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            baseResponse = new BaseResponse(StatusCode.FAIL, e.getMessage());
        }
        return baseResponse;
    }
}
