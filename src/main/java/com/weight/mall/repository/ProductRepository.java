package com.weight.mall.repository;

import com.weight.mall.domain.Product;
import com.weight.mall.dto.request.product.PostRequestDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//    Optional<Product> save(Product product);
//    Optional<Product> findById(Long id);
//    void delete(Long id);
}
