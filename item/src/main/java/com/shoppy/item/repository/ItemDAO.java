package com.shoppy.item.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.shoppy.item.model.Item;

@RepositoryRestResource()
public interface ItemDAO extends JpaRepository<Item, Long> {

    // search items with given ids
    @Query("SELECT i FROM Item i WHERE i.id IN :itemIds")
    List<Item> findByIds(@Param("itemIds") List<Integer> ids);

    // method for searching items in search field which match keyword
    @Query("SELECT i FROM Item i WHERE i.name LIKE %:searchKeyword%")
    List<Item> findByNameContaining(@Param("searchKeyword") String searchKeyword);
}
