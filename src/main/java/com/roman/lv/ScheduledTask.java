package com.roman.lv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component

public class ScheduledTask {
    @Autowired
    WorkerBean workerBean;

     @Scheduled(fixedRate = 5000)
     public void doWork() throws Exception {
                 System.out.println("inside the worker");

                   workerBean.checkForNewArrivals();

        }


    }

