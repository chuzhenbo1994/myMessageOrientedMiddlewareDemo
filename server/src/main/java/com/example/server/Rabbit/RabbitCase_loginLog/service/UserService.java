package com.example.server.Rabbit.RabbitCase_loginLog.service;

import com.example.server.Rabbit.RabbitCase_loginLog.domain.User;
import com.example.server.Rabbit.RabbitCase_loginLog.domain.UserLogDto;
import com.example.server.Rabbit.RabbitCase_loginLog.rabbitmq.publish.LogPublisher;
import com.example.server.Rabbit.RabbitCase_loginLog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
