package com.example.model.Text;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        try {
            userRegService.userRegWithZKLock(userRegDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}
