package com.example.product.service;

import com.example.product.model.ProductRequest;
import com.example.product.model.ProductResponse;

public interface ProductService {

    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);
}
