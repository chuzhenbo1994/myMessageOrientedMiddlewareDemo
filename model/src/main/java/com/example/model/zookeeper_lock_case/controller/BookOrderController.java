package com.example.model.zookeeper_lock_case.controller;


import com.example.model.Text.BaseResponse;
import com.example.model.Text.StatusCode;
import com.example.model.Text.UserRegDto;
import com.example.model.Text.UserRegService;
import com.example.model.zookeeper_lock_case.domain.BookDto;
import com.example.model.zookeeper_lock_case.service.BookOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/book")
@Controller
public class BookOrderController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String prefix = "/user/";

    @Autowired
    private BookOrderService bookOrderService;

    @RequestMapping(value = prefix + "/rub", method = RequestMethod.GET)
    public BaseResponse rub(BookDto bookDto){
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        try {

            Boolean bookOrder = bookOrderService.findUserByBookOrder(bookDto);
            if (bookOrder){

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}
