package com.example.server.Redis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Transactional
    //@Query(value = "select * from item where code = ?1")
    Item findByCode(String code);
    @Transactional
    Item  findItemByCode(String coder);

}
