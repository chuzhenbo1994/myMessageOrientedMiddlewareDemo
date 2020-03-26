package com.example.model.zookeeper_lock_case.repository;

import com.example.model.zookeeper_lock_case.domain.BookOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface BookOrderRepository extends JpaRepository<BookOrder, Long> {

    List<BookOrder> findBookOrdersByUserIdAndBookId(Integer userId,Integer bookId);
}
