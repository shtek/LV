package com.roman.lv;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

//@SpringBootTest
public class ProxyTesting {
    @Autowired
    ProxyPopulator proxyPopulator;
    @Autowired
    FreeProxySelector  proxySelector;
  //  @Test
    public void testProxyAddreses(){
        System.out.println("-----");
        proxyPopulator.populateProxies();
        System.out.println( "proxies" +  proxySelector.webProxies.size());
        System.out.println(proxySelector.getProxy().address().toString());
     //   System.out.println(strisngs.size());
      //  strisngs.stream().forach(s -> System.out.println(s));
    }
}
