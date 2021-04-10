package com.roman.lv;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingDeque;
@Service
public class FreeProxySelector  {
//List<InetSocketAddress> addresses = Collections.synchronizedList(new ArrayList<>());
//create a populate method, that shall populate the list
//make it a set
//see how to remove the item from the list if no longer valid
Deque<Proxy> webProxies = new LinkedBlockingDeque<>();
 //Proxy webProxy
 //           = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("103.28.121.58", 80));
 //public  Proxy getRandomProxy(){
///     return getRandom(webProxies).get();
// }
 public void addProxy(Proxy webProxy){
     webProxies.addLast(webProxy);
 }
 /*public  void removeProxy(Proxy webProxy){
     webProxies.remove(webProxy);
 }
 */
  /*
  or return null;
   */
 public Proxy getProxy() {

     while (webProxies.size() > 0) {

         Proxy webProxy = webProxies.removeFirst();

         if (testConnection(webProxy)) {
             return webProxy;

         }
     }
     return null;
 }

 public boolean testConnection(Proxy webProxy) {
        boolean connectionStatus=false;

        try {
            InetAddress addr=InetAddress.getByName(webProxy.address().toString().replace("/","").split(":")[0]);//here type proxy server ip
            connectionStatus=addr.isReachable(1000); // 1 second time for response

        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }

        return connectionStatus;
    }
    public static <E> Optional<E> getRandom (Collection<E> e) {

        return e.stream()
                .skip((int) (e.size() * Math.random()))
                .findFirst();
    }


}

