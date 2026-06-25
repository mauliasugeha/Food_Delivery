package com.example.order_management.dto;

import jakarta.validation.constraints.*;

public class ProductRequest {
    @NotBlank(message = "Nama produk wajib diisi")
    private String name;

    @NotNull(message = "Harga wajib diisi")
    @Positive(message = "Harga harus lebih dari 0")
    @DecimalMin(value = "1.0", message = "Harga minimal 1")
    @Min(value = 1, message = "Harga minimal 1")
    private Double price;

    @NotNull(message = "Stok wajib diisi")
    @Min(value = 0, message = "Stok tidak boleh negatif")
    private Integer stock;

    @NotNull(message = "Category wajib diisi")
    private Long categoryId; // 🔥 penting untuk relasi

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public Long getCategoryId() {
        return categoryId;
    }
}