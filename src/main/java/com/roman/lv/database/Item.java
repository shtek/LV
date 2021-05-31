package com.roman.lv.database;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    @NaturalId
    private String url;

    @OneToMany(mappedBy = "item")
    private Set<Subscription> subscritions;
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

    public Set<Subscription> getSubscritions() {
        return subscritions;
    }

    public void setSubscritions(Set<Subscription> subscritions) {
        this.subscritions = subscritions;
    }
}
