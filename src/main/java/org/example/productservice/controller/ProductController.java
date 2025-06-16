package org.example.productservice.controller;

import jakarta.validation.Valid;
import org.example.productservice.dao.ProductRequest;
import org.example.productservice.dao.ProductResponse;
import org.example.productservice.dao.ProductSearchRequest;
import org.example.productservice.dao.ProductUpdateRequest;
import org.example.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProducts(@Valid @RequestBody ProductRequest productRequest) {
        return productService.createProducts(productRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable long id, @Valid @RequestBody ProductUpdateRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        return productService.deleteProducts(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable long id) {
        return productService.getProductById(id);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(@Valid @RequestBody ProductSearchRequest productRequest) {
        return productService.getAllProducts(productRequest);
    }
}
