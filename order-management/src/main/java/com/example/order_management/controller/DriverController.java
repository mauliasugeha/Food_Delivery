package com.example.order_management.controller;

import com.example.order_management.entity.Driver;
import com.example.order_management.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @GetMapping
    public List<Driver> getAll() {
        return driverService.getAll();
    }

    @PostMapping
    public Driver create(@RequestBody Driver driver) {
        return driverService.create(driver);
    }
}