package com.roman.lv.controllers;

import com.roman.lv.database.Customer;
import com.roman.lv.database.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping(path="/database") // This means URL's start with /demo (after Application path)
public class MainContoller {
/*
    @Autowired
    private CustomerRepository customerRepository;
    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody
    String addNewCustomer (@RequestParam String email) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Customer customer = new Customer();
        customer.setEmail(email);
      customerRepository.save(customer);
        return "Saved";
    }
    @GetMapping(path="/all")
    public @ResponseBody Iterable<Customer> getAllCustomers() {
        // This returns a JSON or XML with the users
        return customerRepository.findAll();
    }

 */
}
