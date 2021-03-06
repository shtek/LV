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
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
public class LoadResourceConfig {
    //@Value("classpath:/static/items.txt")
    private Resource resource = new DefaultResourceLoader().getResource("classpath:/static/items.txt");

    public Set<String> items;
    public LoadResourceConfig(){
        setItems();
        //ResourceLoader resourceLoader = new DefaultResourceLoader();
        // resource = resourceLoader.getResource("classpath:/static/items.txt");
    }
    private  String asString() {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch ( IOException e) {
            throw new UncheckedIOException(e);
        }
    }
      /*
    Add all items from property file
     */
    public void setItems() {
        String lines[] = asString().split("\\r?\\n");
        Set<String>  temp =  new HashSet<String>(Arrays.asList(lines));
        // items = new HashSet<String>(Arrays.asList(lines));

        items =ConcurrentHashMap.newKeySet();

        items.addAll(temp);

    }
    public Set<String> getItems(){
        return items;
    }
    public void removeItems(Set<String> set){
        items.removeAll(set);
    }

    public void removeItem(String set){
        items.remove(set);
    }

}
