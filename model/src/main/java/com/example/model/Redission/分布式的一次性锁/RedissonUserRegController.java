package com.example.model.Redission.分布式的一次性锁;


import com.example.model.Text.BaseResponse;
import com.example.model.Text.StatusCode;
import com.example.model.Text.UserRegDto;
import com.example.model.Text.UserRegService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/redisson")
@Controller
public class RedissonUserRegController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String prefix = "/user";

    @Autowired
    private RedissonUserRegService userRegService;

    @RequestMapping(value = prefix + "/submit", method = RequestMethod.GET)
    public BaseResponse reg(UserRegDto userRegDto){
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        try {
            userRegService.userRegWithRedisson(userRegDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
