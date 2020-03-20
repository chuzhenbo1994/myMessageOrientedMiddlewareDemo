package com.example.server.RabbitCase.service;

import com.example.server.RabbitCase.domain.User;
import com.example.server.RabbitCase.domain.UserLogDto;
import com.example.server.RabbitCase.rabbitmq.publish.LogPublisher;
import com.example.server.RabbitCase.repository.UserRepository;
import com.example.server.Redis.Redis_抢红包案例.reponse.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LogPublisher logPublisher;

    public Boolean login(UserLogDto userLogDto) {

        User user = userRepository.
                findUserByUserNameAndPassword(userLogDto.getUserName(), userLogDto.getPassWord());

        if (user != null) {
            userLogDto.setUserId(user.getId());
            logPublisher.sendMsg(userLogDto);
            return true;
        }
        //发送登录成功的日志  这里就需要用到redis
        else {
            return false;
        }
    }
}
