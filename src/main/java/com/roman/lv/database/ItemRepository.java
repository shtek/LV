package com.roman.lv.database;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Integer>  {
    List<Item> findByUrl(String url);


}
