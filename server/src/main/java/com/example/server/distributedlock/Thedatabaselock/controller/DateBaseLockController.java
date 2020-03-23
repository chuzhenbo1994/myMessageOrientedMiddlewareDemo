package com.example.server.distributedlock.Thedatabaselock.controller;

import com.example.server.Rabbit.RabbitCase_overtimeOrder.domain.UserOrderDto;
import com.example.server.Redis.Redis_抢红包案例.reponse.BaseResponse;
import com.example.server.Redis.Redis_抢红包案例.reponse.StatusCode;
import com.example.server.distributedlock.Thedatabaselock.domain.UserAccount;
import com.example.server.distributedlock.Thedatabaselock.service.DataBaseLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController()
public class DateBaseLockController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String prefix = "/db";

    @Autowired
    private DataBaseLockService dataBaseLockService;

    @RequestMapping(method = RequestMethod.POST, value = prefix + "/push")
    @ResponseBody
    public BaseResponse login(@RequestBody @Validated UserAccount userAccount, BindingResult result) {
        if (userAccount.getAmount() == null || userAccount.getUserId() == null) {
            return new BaseResponse(StatusCode.INVALIDPARAMS);
        }
        BaseResponse baseResponse = new BaseResponse(StatusCode.SUCCESS);
        try {
            dataBaseLockService.takeMoney(userAccount);
            dataBaseLockService.takeMoneyWithLock(userAccount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baseResponse;
    }

}
