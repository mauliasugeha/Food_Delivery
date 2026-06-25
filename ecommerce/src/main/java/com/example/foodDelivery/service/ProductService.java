// package com.example.foodDelivery.service;

// import com.example.foodDelivery.entity.Product;
// import com.example.foodDelivery.kafka.ProductProducer;
// import com.example.foodDelivery.repository.ProductRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import java.util.List;
// import java.util.Optional;

// @Service
// public class ProductService {
//     @Autowired
//     private ProductRepository productRepository;

//     @Autowired
//     private ProductProducer productProducer;

//     public List<Product> getAllProducts() {
//         return productRepository.findAll();
//     }

//     public Optional<Product> getProductById(Long id) {
//         return productRepository.findById(id);
//     }

//     public Product createProduct(Product product) {
//         Product saved = productRepository.save(product);

//         productProducer.sendMessage("Product created: " + saved.getName());

//         return saved;
//     }

//     public Product updateProduct(Long id, Product request) {
//         return productRepository.findById(id)
//                 .map(product -> {
//                     product.setName(request.getName());
//                     product.setPrice(request.getPrice());
//                     product.setStock(request.getStock());
//                     return productRepository.save(product);
//                 })
//                 .orElse(null);
//     }

//     public boolean deleteProduct(Long id) {
//         if (productRepository.existsById(id)) {
//             productRepository.deleteById(id);
//             return true;
//         }
//         return false;
//     }
// }
