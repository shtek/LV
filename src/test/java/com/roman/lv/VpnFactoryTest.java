package com.roman.lv;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VpnFactoryTest {
    @Autowired
    VpnFactory vpnFactory;
@Test
    public void testVpn(){
   vpnFactory.startVPN();
}
}
