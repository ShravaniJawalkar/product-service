package org.example.productservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.example.productservice.dao.ProductRequest;
import org.example.productservice.dao.ProductResponse;
import org.example.productservice.dao.ProductUpdateRequest;
import org.example.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<List<ProductResponse>> getAllProducts(@Valid @NotNull @RequestParam("category") String category,
                                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                                @RequestParam(value = "size", defaultValue = "10") int size) {
        return productService.getAllProducts(category, page, size);
    }

    @PatchMapping("/{id}/quantity")
    public ResponseEntity<String> updateProductQuantity(@PathVariable long id, @RequestBody Map<String, Object> updates) {
        int quantity = (Integer) updates.get("quantity");
        return productService.updateProductQuantity(id, quantity);
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<String> updateProductName(@PathVariable long id, @RequestBody Map<String, Object> updates) {
        String productName = (String) updates.get("product_name");
        return productService.updateProductName(id, productName);
    }

    @PatchMapping("/{id}/price")
    public ResponseEntity<String> updateProductPrice(@PathVariable long id, @RequestBody Map<String, Object> updates) {
        double price = (Double) updates.get("price");
        return productService.updateProductPrice(id, price);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateProductStatus(@PathVariable long id, @RequestBody Map<String, Object> updates) {
        String status = (String) updates.get("status");
        return productService.updateProductStatus(id, status);
    }
}
