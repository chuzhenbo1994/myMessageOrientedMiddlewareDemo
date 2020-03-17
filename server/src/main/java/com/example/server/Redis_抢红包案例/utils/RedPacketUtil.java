package com.example.server.Redis_抢红包案例.utils;

import java.math.BigDecimal;
import java.util.*;


public class RedPacketUtil {

    public static List<Integer> divideRedPackage(Integer totalAmount, Integer totalPeopleNum) {

        List<Integer> bigDecimals = new ArrayList<>();
        if (totalAmount > 0 && totalPeopleNum > 0) {
            Integer restAmount = totalAmount;

            Integer restPeopleNum = totalPeopleNum;
            Random random = new Random();
            for (int i = 0; i < totalPeopleNum - 1; i++) {
                int amount = random.nextInt(restAmount / restPeopleNum * 2 - 1) + 1;
                restAmount -= amount;
                restPeopleNum--;
                bigDecimals.add(amount);
            }
            bigDecimals.add(restAmount);
        }
        return bigDecimals;
    }
}
