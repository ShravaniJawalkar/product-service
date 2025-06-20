package org.example.productservice.repository;


import org.example.productservice.dao.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findProductsByCategory_CategoryId(Long categoryId, Pageable pageable);
}
