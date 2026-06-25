package com.example.order_management.controller;

import com.example.order_management.entity.Category;
import com.example.order_management.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;
    @GetMapping
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category request) {
        return new ResponseEntity<>(categoryRepository.save(request), HttpStatus.CREATED);
    }
}
