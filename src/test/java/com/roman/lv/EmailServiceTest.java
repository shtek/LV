package com.roman.lv;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {
    @Autowired
    EmailService emailService;
   // @Test
    public void testEmail()
    {
        String [] arr = {"magicroman@gmail.com","olapidu@gmail.com"};
        emailService.sendSimpleMessage("This is just a test" , arr);
    }




}
