package com.example.order_management.repository;

import com.example.order_management.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    List<Driver> findByStatus(String status); // penting buat assign driver
}