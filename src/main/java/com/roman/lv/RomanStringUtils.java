package com.roman.lv;

public class RomanStringUtils {
  /*
  Url is separate from Emials by DIVIDER
   */
public static String getURL (String urlAndEmails){
    String [] urlAndemail = urlAndEmails.split("DIVIDER");
    String url = urlAndemail[0];
    return  url;
}
    public static String[] getEmails (String urlAndEmails){
        String [] urlAndemail = urlAndEmails.split("DIVIDER");
        String [] emails = urlAndemail[1].split("SPLITTER");
        return  emails;
    }



}

