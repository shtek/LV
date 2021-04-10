package com.roman.lv;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProxyPopulator {
    @Autowired
    FreeProxySelector proxySelector;

    public String getProxAddressesHTML(){
        String proxySite = "https://free-proxy-list.net";
        String response = null;
        HttpURLConnection con = null;
        try {
           URL url = new URL(proxySite);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            response =  content.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  response;
    }

   public List<InetSocketAddress> getAddresses(){
       String html =  getProxAddressesHTML();
       String body = StringUtils.substringBetween(html, "<tbody>", "</tbody>");
       String []strs =  body.split("<tr>");
       List<String> list = Arrays.asList(strs);
       //filter Anonimus and Elite
       List<String> strings = filterEliteProxy(list);

       //assemble host and port into inetSocketAddress
       List<InetSocketAddress> add = getProxyAddresses(strings);

       return add;

   }
   //add proxies to the queue
  public void populateProxies(){
      List<InetSocketAddress>  add = getAddresses();
      add.stream().forEach(a->proxySelector.addProxy(new Proxy(Proxy.Type.HTTP,a)));
  }
public List<String> filterEliteProxy (List<String > list){
   return  list.stream().filter( s->  {
        return s.contains("elite") || s.contains("anonymus");
    }).collect(Collectors.toList());
}
public  List<InetSocketAddress> getProxyAddresses(List<String > strings){
    List<InetSocketAddress> add = strings.stream().map(s->   {//do somehting
        String  host =  StringUtils.substringBetween(s,"<td>","</td>");
        String port =  StringUtils.substringBetween(s,"</td><td>","</td><td>");
        int portInt = Integer.parseInt(port);
        InetSocketAddress socketAddress = new InetSocketAddress(host, portInt);
        return socketAddress;

    }).collect(Collectors.toList());
    return add;
}
}
