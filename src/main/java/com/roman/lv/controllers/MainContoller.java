package com.roman.lv.controllers;

import com.roman.lv.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path="/database") // This means URL's start with /demo (after Application path)
public class MainContoller {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private DAO dao;
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @PostMapping(path="/addSubscription") // Map ONLY POST Requests
    public @ResponseBody    Subscription addNewSubscription (@RequestParam String url,@RequestParam String email) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        return dao.addNewSubscription(url,email);

    }



    @PostMapping(path="/addCustomer") // Map ONLY POST Requests
    public @ResponseBody    Customer addNewCustomer (@RequestParam String email) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        return dao.addNewCustomer(email);
    }



    @PostMapping(path="/addItem") // Map ONLY POST Requests
    public @ResponseBody Item addNewItem (@RequestParam String url) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
    return  dao.addNewItem(url);
    }

    @GetMapping(path="/allCustomers")
    public @ResponseBody Iterable<Customer> getAllCustomers() {
        // This returns a JSON or XML with the users
        return customerRepository.findAll();
    }
    @GetMapping(path="/allSubscriptions")
    public @ResponseBody Iterable<Subscription> getAllSubscriptionss() {
        // This returns a JSON or XML with the users
        return subscriptionRepository.findAll();
    }
    @GetMapping(path="/customer/{email}")
    public @ResponseBody Iterable<Customer> getItemByEmail(@PathVariable String email) {
        // This returns a JSON or XML with the users
        return customerRepository.findByEmail(email);
    }

    @GetMapping(path="/allItems")
    public @ResponseBody Iterable<Item> getAllItems() {
        // This returns a JSON or XML with the users
        return itemRepository.findAll();
    }
  @GetMapping(path="/item/{url}")
    public @ResponseBody Iterable<Item> getItemByUrl(@PathVariable String url) {
        // This returns a JSON or XML with the users
      return itemRepository.findByUrl(url);
    }

}
