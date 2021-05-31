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

}
