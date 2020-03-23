package com.example.server.distributedlock.Thedatabaselock.service;

import com.example.server.Rabbit.RabbitCase_overtimeOrder.domain.UserOrderDto;
import com.example.server.Redis.Redis_抢红包案例.reponse.BaseResponse;
import com.example.server.distributedlock.Thedatabaselock.domain.UserAccount;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
public class DataBaseLockService {

    public void takeMoney(UserAccount userAccount) {
    }

    public void takeMoneyWithLock(UserAccount userAccount) {
    }
}
