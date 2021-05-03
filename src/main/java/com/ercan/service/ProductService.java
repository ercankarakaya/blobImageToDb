package com.ercan.service;

import com.ercan.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    void save(Product product);
    List<Product> getAllActiceProducts();
    Optional<Product> getProductById(Long id);

}
