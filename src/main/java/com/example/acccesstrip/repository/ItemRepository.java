package com.example.acccesstrip.repository;

import com.example.acccesstrip.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Items, Long> {
}
