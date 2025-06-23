package org.example.productservice.service;


import org.example.productservice.dao.*;
import org.example.productservice.repository.CategoryRepository;
import org.example.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Transactional
    public ResponseEntity<ProductResponse> createProducts(ProductRequest productRequest) {
        Product product = Product.builder()
                .productName(productRequest.getProductName())
                .productDescription(productRequest.getDescription())
                .productQuantity(productRequest.getStockQuantity())
                .productPrice(productRequest.getPrice())
                .currency(productRequest.getCurrency())
                .productStatus(productRequest.getProductStatus())
                .build();
        categoryRepository.getCategoryByCategoryName(productRequest.getProductCategory()).ifPresentOrElse(
                product::setCategory,
                () -> {
                    throw new RuntimeException("Category not found: " + productRequest.getProductCategory());
                }
        );
        Product savedProduct = productRepository.save(product);

        ProductResponse productResponse = ProductResponse.builder()
                .productName(savedProduct.getProductName())
                .productDescription(savedProduct.getProductDescription())
                .categoryName(savedProduct.getCategory().getCategoryName())
                .price(savedProduct.getProductPrice())
                .quantity(savedProduct.getProductQuantity())
                .build();
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);

    }

    @Transactional
    public ResponseEntity<String> updateProduct(long id, ProductUpdateRequest productRequest) {
        productRepository.findById(id).ifPresentOrElse(
                product -> {
                    product.setProductName(productRequest.getProductName());
                    product.setProductPrice(productRequest.getPrice());
                    product.setProductDescription(productRequest.getProductDescription());
                    product.setProductQuantity(productRequest.getQuantity());
                    product.setProductStatus(ProductStatus.valueOf(productRequest.getProductStatus()));
                    productRepository.save(product);
                },
                () -> {
                    throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Product not found with id: " + id);
                }
        );
        return new ResponseEntity<>("Product Updated SuccessFully", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteProducts(long id) {
        if (!validateIfProductExist(id)) {
            return new ResponseEntity<>("Product not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        productRepository.deleteById(id);
        return new ResponseEntity<>("Product Deleted Successfully", HttpStatus.OK);
    }

    private boolean validateIfProductExist(long id) {
        return productRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<ProductResponse>> getAllProducts(ProductSearchRequest productRequest) {
        Category category = categoryRepository.getCategoryByCategoryName(productRequest.getCategory()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Category not found"));
        PageRequest pageRequest = PageRequest.of(productRequest.getPage(), productRequest.getSize());
        Page<Product> products = productRepository.findProductsByCategory_CategoryId(category.getCategoryId(), pageRequest);
        if (products.isEmpty()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT); // Return empty list if no products found
        }

        List<ProductResponse> productResponses = new ArrayList<>();
        products.forEach(product -> {
            ProductResponse productResponse = ProductResponse.builder()
                    .productName(product.getProductName())
                    .productDescription(product.getProductDescription())
                    .categoryName(product.getCategory().getCategoryName())
                    .price(product.getProductPrice())
                    .quantity(product.getProductQuantity()).build();
            productResponses.add(productResponse);
        });
        return new ResponseEntity<>(productResponses, HttpStatus.OK);
    }

    public ResponseEntity<ProductResponse> getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Product not found with id: " + productId));
        ProductResponse productResponse = ProductResponse.builder()
                .productName(product.getProductName())
                .productDescription(product.getProductDescription())
                .categoryName(product.getCategory().getCategoryName())
                .quantity(product.getProductQuantity())
                .price(product.getProductPrice()).build();
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> updateProductQuantity(long id, int quantity) {
        int response = productRepository.updateProductQuantity(id, quantity);
        if (response == 0) {
            return new ResponseEntity<>("Product not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Product quantity updated successfully", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> updateProductPrice(long id, double price) {
        int response = productRepository.updateProductPrice(id, price);
        if (response == 0) {
            return new ResponseEntity<>("Product not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Product price updated successfully", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> updateProductName(long id, String name) {
        int response = productRepository.updateProductName(id, name);
        if (response == 0) {
            return new ResponseEntity<>("Product not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Product name updated successfully", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> updateProductStatus(long id, String status) {
        int response = productRepository.updateProductStatus(id, ProductStatus.valueOf(status));
        if (response == 0) {
            return new ResponseEntity<>("Product not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Product status updated successfully", HttpStatus.OK);
    }
}
