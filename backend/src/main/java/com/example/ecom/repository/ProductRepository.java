package com.example.ecom.repository;

import com.example.ecom.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select p.* from product p" +
            " left join category c on p.id = c.id " +
            " where p.name like %:name% or c.name like %:name%", nativeQuery = true)
    List<Product> getAllBySearch(@Param("name") String name);

//List<Product> findAllByNameContaining(String title);

}
