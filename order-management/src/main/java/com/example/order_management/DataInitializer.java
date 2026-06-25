package com.example.order_management;

import com.example.order_management.entity.Customer;
import com.example.order_management.entity.Product;
import com.example.order_management.entity.Category;
import com.example.order_management.entity.Driver;
import com.example.order_management.repository.CustomerRepository;
import com.example.order_management.repository.ProductRepository;
import com.example.order_management.repository.CategoryRepository;
import com.example.order_management.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Autowired
    private DriverRepository driverRepository;

    @Override
    public void run(String... args) {
        if (customerRepository.count() == 0) {

            // 👤 Customer
            Customer customer1 = new Customer("Budi", "budi@mail.com", "Malang");
            Customer customer2 = new Customer("Siti", "siti@mail.com", "Surabaya");

            customerRepository.save(customer1);
            customerRepository.save(customer2);

            Category makanan = new Category("Makanan", "Makanan berat");
            Category minuman = new Category("Minuman", "Minuman segar");

            categoryRepository.save(makanan);
            categoryRepository.save(minuman);

            // 🍛 Product (menu makanan)
            productRepository.save(new Product("Nasi Goreng", 20000.0, 50, makanan));
            productRepository.save(new Product("Mie Goreng", 18000.0, 40, makanan));
            productRepository.save(new Product("Es Teh", 5000.0, 100, minuman));

            // 🚚 Driver
            driverRepository.save(new Driver("Andi", "Motor", "AVAILABLE"));
            driverRepository.save(new Driver("Joko", "Motor", "AVAILABLE"));

            System.out.println("Food Delivery Data Initialized!");
        }

        System.out.println("Data initialized successfully!");
    }
}
