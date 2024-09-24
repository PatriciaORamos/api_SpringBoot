package com.example.springboot.services.impl;

import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import com.example.springboot.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    public ProductModel saveProduct(ProductModel productModel) {
        return productRepository.save(productModel);
    }

    public List<ProductModel> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<ProductModel> getOneProduct(UUID id) {
        return productRepository.findById(id);
    }

    public Object updateProductById(ProductModel productModel) {
        return productRepository.save(productModel);
    }

    public void deleteProduct(ProductModel product0) {
        productRepository.delete(product0);
    }
}
