package com.roman.lv;
/*
I am on dev
 */
import org.springframework.stereotype.Service;

@Service
public class Counter {
    private  boolean counter = false;
    public  synchronized   boolean  getCounter() {
        return counter;
    }

    public synchronized void setCounter(boolean counter) {
        this.counter = counter;
    }
}
