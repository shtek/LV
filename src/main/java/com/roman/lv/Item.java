package com.roman.lv;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Set;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String url;
    //this will represent our subscribers,
    //if list Empty this means that there no subscribers for this item.
    private Set<Customer> subscribers;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Set<Customer> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<Customer> subscribers) {
        this.subscribers = subscribers;
    }
}
