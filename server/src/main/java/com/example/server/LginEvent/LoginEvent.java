package com.example.server.LginEvent;

import lombok.Data;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;

import java.io.Serializable;

@Data
@ToString
public class LoginEvent extends ApplicationEvent  implements Serializable {
    Logger logger = LoggerFactory.getLogger(getClass());

    private String userName ;
    private String loginTime;
    private String ip;

    public LoginEvent(Object source, String userName, String loginTime, String ip) {
        super(source);
        this.userName = userName;
        this.loginTime = loginTime;
        this.ip = ip;
    }

    public LoginEvent(Object source) {
        super(source);
    }
}
