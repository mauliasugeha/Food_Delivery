package com.example.order_management.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String vehicle;
    private String status; // AVAILABLE / BUSY

    public Driver() {}

    public Driver(String name, String vehicle, String status) {
        this.name = name;
        this.vehicle = vehicle;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVehicle() {
        return vehicle;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}