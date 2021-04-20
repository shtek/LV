package com.roman.lv;

import org.apache.http.util.Asserts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


public class RomanStringUtilsTest {
    @Test
    public void testUtils(){
        String input = "https://us.louisvuitton.com/eng-us/products/pochette-accessoires-monogram-005656DIVIDERchristinajoybrown@gmail.comSPLITTER";
        String url = RomanStringUtils.getURL(input);
        Assertions.assertEquals("https://us.louisvuitton.com/eng-us/products/pochette-accessoires-monogram-005656",url);
        String [] emails = RomanStringUtils.getEmails(input);
        Assertions.assertEquals(emails.length ,1);
        String input2 = "https://us.louisvuitton.com/eng-us/products/pochette-accessoires-monogram-005656DIVIDERchristinajoybrown@gmail.comSPLITTERhahsja@yahoo.com";
        String [] emails2 = RomanStringUtils.getEmails(input2);
        Assertions.assertEquals(emails2[0] ,"christinajoybrown@gmail.com");


    }
}
