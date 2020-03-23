package com.example.server.Rabbit.RabbitCase_overtimeOrder.controller;

import com.example.server.Rabbit.RabbitCase_loginLog.domain.UserLogDto;
import com.example.server.Rabbit.RabbitCase_overtimeOrder.domain.UserOrder;
import com.example.server.Rabbit.RabbitCase_overtimeOrder.domain.UserOrderDto;
import com.example.server.Rabbit.RabbitCase_overtimeOrder.service.DeadOrderService;
import com.example.server.Redis.Redis_抢红包案例.reponse.BaseResponse;
import com.example.server.Redis.Redis_抢红包案例.reponse.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@Controller
@RequestMapping("/middleware")
public class UserOrderController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String prefix = "/user/order";
    @Autowired
    private DeadOrderService deadOrderService;

    @RequestMapping(method = RequestMethod.POST, value = prefix + "/push", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse login(@RequestBody @Validated UserOrderDto userOrderDto, BindingResult result) {

        if (result.hasErrors()) {
            return new BaseResponse(StatusCode.INVALIDPARAMS);
        }
        BaseResponse baseResponse = new BaseResponse(StatusCode.SUCCESS);

        try {
            deadOrderService.pushUserOrder(userOrderDto);
            baseResponse = new BaseResponse(StatusCode.FAIL, "登录失败");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return baseResponse;
    }
}
