package com.roman.lv;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.nio.charset.StandardCharsets.UTF_8;
@Configuration
public class ExpressVpnResourceLoader {
    Set<String> vpnServers = new HashSet();
    private Resource resource = new DefaultResourceLoader().getResource("classpath:/static/expressVpnServers.txt");
   public ExpressVpnResourceLoader()
   {
       setItems(resource);
   }


    private  String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch ( IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    public void setItems(Resource resource) {
        String lines[] = asString(resource).split("\\r?\\n");

        vpnServers = new HashSet<String>(Arrays.asList(lines));
    }
    public Set<String> getItems(){
        return vpnServers;
    }
    }
