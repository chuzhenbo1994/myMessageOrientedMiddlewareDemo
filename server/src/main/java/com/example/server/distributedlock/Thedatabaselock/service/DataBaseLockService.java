package com.example.server.distributedlock.Thedatabaselock.service;

import com.example.server.Rabbit.RabbitCase_overtimeOrder.domain.UserOrderDto;
import com.example.server.Redis.Redis_抢红包案例.reponse.BaseResponse;
import com.example.server.distributedlock.Thedatabaselock.domain.UserAccount;
import com.example.server.distributedlock.Thedatabaselock.domain.UserAccountDto;
import com.example.server.distributedlock.Thedatabaselock.domain.UserAccountRecord;
import com.example.server.distributedlock.Thedatabaselock.repository.UserAccountRecordRepository;
import com.example.server.distributedlock.Thedatabaselock.repository.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class DataBaseLockService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserAccountRecordRepository userAccountRecordRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    public void takeMoney(UserAccountDto userAccountDto) throws Exception {
        UserAccount userAccountByUserId = userAccountRepository.findUserAccountByUserId(userAccountDto.getUserId());
        if (userAccountByUserId != null && (userAccountByUserId.getAmount().doubleValue() - userAccountDto.getAmount() > 0)) {
            //余额是大于所请求的金额 执行逻辑
            // 更新数据库数据
            userAccountRepository.updateAmount(BigDecimal.valueOf(userAccountByUserId.getAmount().
                    doubleValue() - userAccountDto.getAmount()), userAccountByUserId.getId());
            //  记录成功体现时的记录

            UserAccountRecord accountRecord = new UserAccountRecord();
            accountRecord.setCreateTime(new Date());
            accountRecord.setAccountId(userAccountByUserId.getId());
            accountRecord.setMoney(BigDecimal.valueOf(userAccountDto.getAmount()));

            UserAccountRecord userAccountRecord = userAccountRecordRepository.save(accountRecord);

            logger.info("当前待提现的金额为：{}，用户账户余额为：{},存储的用户取款记录是{}",
                    userAccountDto.getAmount(), userAccountByUserId.getAmount().doubleValue()
                            - userAccountDto.getAmount(), userAccountRecord.toString());
        } else {
            throw new Exception("账户不存在或者账户余额不足");
        }

    }

    // 数据库 乐观锁提取金额的方式--
    public void takeMoneyWithLock(UserAccountDto userAccountDto) throws Exception {
        UserAccount userAccountByUserId = userAccountRepository.findUserAccountByUserId(userAccountDto.getUserId());

        if (userAccountByUserId != null && (userAccountByUserId.getAmount().doubleValue() - userAccountDto.getAmount() > 0)) {
            int res = userAccountRepository.updateAccountByPKVersion(
                    BigDecimal.valueOf(userAccountByUserId.getAmount().doubleValue() - userAccountDto.getAmount())
                    , userAccountByUserId.getId(), userAccountByUserId.getVersion());
            if (res > 0) {
                UserAccountRecord accountRecord = new UserAccountRecord();
                accountRecord.setAccountId(userAccountByUserId.getId());
                accountRecord.setCreateTime(new Date());
                accountRecord.setMoney(BigDecimal.valueOf(userAccountDto.getAmount()));
                UserAccountRecord userAccountRecord = userAccountRecordRepository.save(accountRecord);
                logger.info("当前待提现的金额为：{}，用户账户余额为：{},用戶取款前金额为{}",
                        userAccountDto.getAmount(), userAccountByUserId.getAmount().doubleValue()
                                - userAccountDto.getAmount(), userAccountByUserId.getAmount());
            }
        } else {
            throw new Exception("账户不存在或者账户余额不足");
        }

    }

    public void takeMoneyWithLockNegative(UserAccountDto userAccountDto) throws Exception {

        UserAccount userAccountByUserId = userAccountRepository.findByUserId(userAccountDto.getUserId());
        if (userAccountByUserId != null && (userAccountByUserId.getAmount().doubleValue() - userAccountDto.getAmount() > 0)) {
            int res = userAccountRepository.updateAccountByAmountLock(
                    BigDecimal.valueOf(userAccountByUserId.getAmount().doubleValue() - userAccountDto.getAmount())
                    , userAccountByUserId.getId());
            if (res > 0) {
                UserAccountRecord userAccountRecord = new UserAccountRecord();
                userAccountRecord.setCreateTime(new Date());
                userAccountRecord.setMoney(BigDecimal.valueOf(userAccountDto.getAmount()));
                userAccountRecord.setAccountId(userAccountByUserId.getId());
                UserAccountRecord save = userAccountRecordRepository.save(userAccountRecord);
                logger.info("当前待提现的金额为：{}，用户账户余额为：{},用戶取款前金额为{}",
                        userAccountDto.getAmount(), userAccountByUserId.getAmount().doubleValue()
                                - userAccountDto.getAmount(), userAccountByUserId.getAmount());
            }

        } else {
            throw new Exception("账户不存在或者账户余额不足");
        }
    }
}
