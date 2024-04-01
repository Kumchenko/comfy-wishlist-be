package com.example.comfywishlist.repository;

import com.example.comfywishlist.entity.WishItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishItemRepository extends JpaRepository<WishItem, Long> {
    List<WishItem> findAll();
    Optional<WishItem> findById(long id);
}
