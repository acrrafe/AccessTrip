package com.example.acccesstrip.repository;

import com.example.acccesstrip.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessTripRepository extends JpaRepository<Account, Long> {
}
