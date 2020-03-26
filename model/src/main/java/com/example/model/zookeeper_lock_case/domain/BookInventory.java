package com.example.model.zookeeper_lock_case.domain;

import javax.persistence.*;

@Entity
@Table(name = "book_inventory")
public class BookInventory {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    @Column(name = "book_id")
    private Integer  bookId;
    @Column(name = "inventory" )
    private Integer inventory;
    @Column(name = "added")
    private Short added;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Short getAdded() {
        return added;
    }

    public void setAdded(Short added) {
        this.added = added;
    }
}
