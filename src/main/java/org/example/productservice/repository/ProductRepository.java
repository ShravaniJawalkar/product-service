package org.example.productservice.repository;


import org.example.productservice.dao.Product;
import org.example.productservice.dao.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findProductsByCategory_CategoryId(Long categoryId, Pageable pageable);

    @Modifying
    @Query(value = "UPDATE Product p SET p.productQuantity =  :quantity WHERE p.productId = :id")
    int updateProductQuantity(@Param("id") long id, @Param("quantity") int quantity);

    @Modifying
    @Query(value = "UPDATE Product p SET p.productPrice =  :price WHERE p.productId = :id")
    int updateProductPrice(@Param("id") long id, @Param("price") double quantity);

    @Modifying
    @Query(value = "UPDATE Product p SET p.productName =  :name WHERE p.productId = :id")
    int updateProductName(@Param("id") long id, @Param("name") String name);

    @Modifying
    @Query(value = "UPDATE Product p SET p.productStatus =  :status WHERE p.productId = :id")
    int updateProductStatus(@Param("id") long id, @Param("status") ProductStatus productStatus);
}
