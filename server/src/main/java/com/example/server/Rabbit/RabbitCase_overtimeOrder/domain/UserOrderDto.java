package com.example.server.Rabbit.RabbitCase_overtimeOrder.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ToString
public class UserOrderDto implements Serializable {

    @NotBlank
    private String orderNo;

    @NotNull
    private Integer userId;
}
