package com.example.server;

import com.example.server.Redis_抢红包案例.utils.RedPacketUtil;
import org.junit.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class redText {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void text() {
        List<Integer> list = RedPacketUtil.divideRedPackage(1000, 10);
        final Float[] sum = {0F};
        list.forEach(a -> {

            BigDecimal divide = new BigDecimal(a).divide(new BigDecimal(100));
            logger.info(() -> {
                sum[0] = sum[0] + Float.parseFloat(divide.toString());
                return divide.toString();
            });
        });
        logger.info(()->sum[0].toString());
    }
}
