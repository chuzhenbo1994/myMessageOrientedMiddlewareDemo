package com.example.server.distributedlock.Thedatabaselock.repository;

import com.example.server.distributedlock.Thedatabaselock.domain.UserAccount;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.math.BigDecimal;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {


    UserAccount findUserAccountByUserId(Integer userId);

    @Modifying
    @Query(value = "update user_account set amount =:amount where id =:Id ", nativeQuery = true)
    @Transactional
    int updateAmount(@Param(value = "amount") BigDecimal amount, @Param(value = "Id") Integer Id);

    @Modifying
    @Query(value = "update user_account set amount =:amount,version = 1+:version  where id =:Id and version = :version", nativeQuery = true)
    @Transactional
    int updateAccountByPKVersion(@Param(value = "amount") BigDecimal amount, @Param(value = "Id") Integer id, @Param(value = "version") Integer version);

    /**
     * 悲观锁
     */

    //	事务开始即获得数据库的锁

    /**
     * 悲观锁没有测试成功不知道什么愿意
     * @param userId
     * @return
     */
    @Transactional
 //   @Lock(value =LockModeType.PESSIMISTIC_WRITE )
    @Query(value = "select * from " +
            "user_account where user_id =:userId for update ",nativeQuery = true)
    UserAccount findByUserId(@Param("userId") Integer userId);


    @Query(nativeQuery = true, value = "update user_account set amount =:amount where id =:Id ")
    @Modifying
    @Transactional
    int updateAccountByAmountLock(@Param(value = "amount") BigDecimal amount, @Param(value = "Id") Integer id);

}
