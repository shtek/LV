package com.roman.lv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component

public class ScheduledTask {
    @Autowired
    WorkerBean workerBean;
    @Autowired
    Counter counter;
       //every 5 seconds

     /*
     Keep looking for items .  Until
      */
  //   @Scheduled(fixedRate = 5000)
     public void doWork() throws Exception {
//will not use counter for now , but will remove the item from scan once found
         // will need to figure out how to put it back for scanning
          //   if (counter.getCounter()==false) {
                 System.out.println("inside the worker");

                   workerBean.checkForNewArrivals();

          //     }

        }


    }

