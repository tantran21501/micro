package com.example.product.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductResponse {
    private String productName;
    private long productId;
    private long quantity;
    private long price;

}
