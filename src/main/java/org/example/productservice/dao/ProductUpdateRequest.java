package org.example.productservice.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUpdateRequest {
    @NotNull
    @JsonProperty("product_name")
    private String productName;
    @NotNull
    @JsonProperty("price")
    private double price;
    @NotNull
    @JsonProperty("quantity")
    private int quantity;
    @NotNull
    @JsonProperty("category_id")
    private Long categoryId;
    @JsonProperty("product_description")
    private String productDescription;
    @NotNull
    @JsonProperty("product_status")
    private String productStatus;

}
