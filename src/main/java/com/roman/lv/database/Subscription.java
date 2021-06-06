package com.roman.lv.database;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Subscription {
    @EmbeddedId
    SubscriptionId id;
    @ManyToOne
    @MapsId("customerId")
    @JoinColumn(name = "customer_id")
    Customer customer;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    Item item;

    LocalDate expiryDate;
    boolean active;

    public SubscriptionId getId() {
        return id;
    }

    public void setId(SubscriptionId id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
