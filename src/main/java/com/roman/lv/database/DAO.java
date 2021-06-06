package com.roman.lv.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class DAO {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    public  Customer addNewCustomer ( String email) {

        List<Customer> list = customerRepository.findByEmail(email);
        if (list.size() > 0) {
            return list.get(0);
        } else {

            Customer customer = new Customer(email);
            customer.setSubscriptions(new HashSet<>());
          return   customerRepository.save(customer);

        }
    }

    public  Item addNewItem (String url) {
        List<Item> list = itemRepository.findByUrl(url);
        if (list.size() > 0) {
            return list.get(0);
        } else {

            Item item = new Item(url);
            item.setSubscriptions(new HashSet<>());
            return itemRepository.save(item);

        }
    }

   //if it is just add new perhaps no need for search
    //if it is add/update then we need to add logic
    //on how to update
    public Subscription addNewSubscription(String url, String email) {
           //if Item is new then create a new Item
          Item item = addNewItem(url);
          Customer customer = addNewCustomer(email);
          List<Subscription> list = subscriptionRepository.findByItemAndCustomer(item,customer);
         if (list.size() > 0) {
            return list.get(0);

        } else {

           Subscription subscription = new Subscription();
           subscription.setItem(item);
           subscription.setCustomer(customer);
           subscription.setActive(true);
           subscription.setExpiryDate(LocalDate.now().plusMonths(1L));
            Subscription subscription2 = subscriptionRepository.save(subscription);
          // item.getSubscriptions().add(subscription);
          // itemRepository.save(item);
         //  customer.getSubscriptions().add(subscription);
          // customerRepository.save(customer);
           return subscription2;
        }

    }
}
