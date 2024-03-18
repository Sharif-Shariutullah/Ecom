package com.example.ecom.repository;

import com.example.ecom.entity.Wishlist;
import lombok.Lombok;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findAllByUserId(Long userId);
}
