package com.example.model.zookeeper_lock_case.repository;

import com.example.model.zookeeper_lock_case.domain.BookInventory;
import com.example.model.zookeeper_lock_case.domain.BookOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookInventoryRepository extends JpaRepository<BookInventory, Long> {
}
