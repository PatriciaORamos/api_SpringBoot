package com.example.springboot.controllers;

import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.models.Message;
import com.example.springboot.models.ProductModel;
import com.example.springboot.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    Message message;

    @Autowired
    ProductService productService;

    @PostMapping("")
    public ResponseEntity<?> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        if (productModel.getName().isBlank()){
            message.setMessage("Name is required.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        } else if (productModel.getValue().toString().equals("0.00")) {
            message.setMessage("Value is required.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(productModel));
        }
    }

    @GetMapping("")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> productList = productService.getAllProducts();
        if(!productList.isEmpty()) {
            for (ProductModel product : productList) {
                UUID id = product.getIdProduct();
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value="id") UUID id) {
        if(id == null) {
            message.setMessage("Id is required.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        Optional<ProductModel> product0 = productService.getOneProduct(id);
        if (product0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(product0.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProductById(@PathVariable(value="id") UUID id,
                                                 @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductModel> product0 = productService.getOneProduct(id);
        if (product0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        var productModel = product0.get();
        BeanUtils.copyProperties(productRecordDto, productModel);

        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProductById(productModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value="id") UUID id) {
        if(id == null) {
            message.setMessage("Id is required.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        Optional<ProductModel> product0 = productService.getOneProduct(id);
        if (product0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        productService.deleteProduct(product0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
    }
}
