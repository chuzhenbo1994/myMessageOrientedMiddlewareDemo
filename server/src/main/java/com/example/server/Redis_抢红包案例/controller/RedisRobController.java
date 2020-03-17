package com.example.server.Redis_抢红包案例.controller;

import com.example.server.Redis_抢红包案例.domain.RedPacketDto;
import com.example.server.Redis_抢红包案例.reponse.BaseResponse;
import com.example.server.Redis_抢红包案例.reponse.StatusCode;
import com.example.server.Redis_抢红包案例.service.IRedPacketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class RedisRobController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String prefix = "red/packet";

    @Autowired
    private IRedPacketService iRedpacketService;

    @RequestMapping(name = prefix+"hand/out",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse handOut(@Validated @RequestBody RedPacketDto redPacketDto, BindingResult result){

        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        if (result.hasErrors()){
            return new BaseResponse(StatusCode.INVALIDPARAMS);
        }
        try {
            String redId = iRedpacketService.handOut(redPacketDto);
            response.setData(redId);

        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse(StatusCode.FAIL,e.getMessage());
        }
        return response;
    }
}
