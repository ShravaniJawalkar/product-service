package org.example.productservice.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSearchRequest {
    @NotNull
    @JsonProperty("category")
    private String category;
    @JsonProperty("page")
    private Integer page = 0;
    @JsonProperty("size")
    private Integer size = 10;
}
