package com.example.order_management.service;

import com.example.order_management.entity.Driver;
import com.example.order_management.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public List<Driver> getAll() {
        return driverRepository.findAll();
    }

    public Driver create(Driver driver) {
        return driverRepository.save(driver);
    }

    public Driver getAvailableDriver() {
        List<Driver> drivers = driverRepository.findByStatus("AVAILABLE");
        return drivers.isEmpty() ? null : drivers.get(0);
    }

    public void setBusy(Driver driver) {
        driver.setStatus("BUSY");
        driverRepository.save(driver);
    }
}