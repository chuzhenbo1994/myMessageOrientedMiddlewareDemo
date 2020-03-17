package com.example.server.Redis_抢红包案例.service;

import com.example.server.Redis_抢红包案例.domain.RedDetail;
import com.example.server.Redis_抢红包案例.domain.RedPacketDto;
import com.example.server.Redis_抢红包案例.domain.RedRecord;
import com.example.server.Redis_抢红包案例.domain.RedRobRecord;
import com.example.server.Redis_抢红包案例.repository.RedDetailRepository;
import com.example.server.Redis_抢红包案例.repository.RedRecordRepository;
import com.example.server.Redis_抢红包案例.repository.RedRobRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@EnableAsync
public class RedServiceImpl implements IRedService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedRecordRepository redRecordRepository;

    @Autowired
    private RedDetailRepository redDetailRepository;

    @Autowired
    private RedRobRecordRepository redRobRecordRepository;


    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void recordRedPacket(RedPacketDto packetDto, String redId, List<Integer> list) {

        RedRecord redRecord = new RedRecord();
        redRecord.setUserId(packetDto.getUserId());
        redRecord.setRed_packet(redId);
        redRecord.setTotal(packetDto.getTotal());
        redRecord.setAmount(BigDecimal.valueOf(packetDto.getAmount()));
        redRecord.setCreatTime(new Date());
        RedRecord save = redRecordRepository.save(redRecord);
        System.out.println(1);
        final RedDetail[] redDetail = new RedDetail[1];
        list.forEach(l -> {
            redDetail[0] = new RedDetail();
            redDetail[0].setRecordId(save.getId());
            redDetail[0].setAmount(BigDecimal.valueOf(l));
            redDetail[0].setCreatTime(new Date());
            RedDetail save1 = redDetailRepository.save(redDetail[0]);
        });
    }

    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void recordRobRedPacket(Integer userId, String redId, BigDecimal amount) throws Exception {
        RedRobRecord redRobRecord = new RedRobRecord();
        redRobRecord.setUserId(userId);
        redRobRecord.setRed_packet(redId);
        redRobRecord.setAmount(amount);
        redRobRecord.setRobTime(new Date());
        RedRobRecord save = redRobRecordRepository.save(redRobRecord);
    }
}
