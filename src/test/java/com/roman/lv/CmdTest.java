package com.roman.lv;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdTest {
  //  @Test
    public void cmdT() throws IOException {
       // Process process = Runtime.getRuntime().exec("ping www.stackabuse.com");
        Process process = Runtime.getRuntime()
                .exec("runas /profile /user:Roman \"openvpn --config nl-free-02.protonvpn.com.udp.ovpn", null, new File("C:\\Users\\Roman\\OpenVPN\\config\\"));
        printResults(process);
    }
    public static void printResults(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}
