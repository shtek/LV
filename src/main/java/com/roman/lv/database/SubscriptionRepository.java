package com.roman.lv.database;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {
    List<Subscription> findByItemAndCustomer(Item url, Customer email);
}
