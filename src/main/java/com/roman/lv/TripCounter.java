package com.roman.lv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Deprecated
public class TripCounter {
@Autowired
    Counter counter;
 @RequestMapping("/trip")
    public String resetCounter(){
     counter.setCounter(false);
     return "counter succesfully tripped->" + counter.getCounter();
 }

}
