package com.example.order_management.controller;

import com.example.order_management.dto.CustomerRequest;
import com.example.order_management.entity.Customer;
import com.example.order_management.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        return customer
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔥 FIX: pakai DTO + @Valid
    @PostMapping
    public ResponseEntity<Customer> createCustomer(
            @RequestBody @Valid CustomerRequest request
    ) {
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setAddress(request.getAddress());

        Customer saved = customerRepository.save(customer);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}