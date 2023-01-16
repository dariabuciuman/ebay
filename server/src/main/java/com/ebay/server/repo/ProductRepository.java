package com.ebay.server.repo;

import com.ebay.server.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();
    Product findProductByProductId(Long productId);

    List<Product> findAllBySellerId(Long sellerId);

    List<Product> findAllByBidderId(Long bidderId);
    void removeProductByProductId(Long productId);
}
