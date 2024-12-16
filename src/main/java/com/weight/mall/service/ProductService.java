package com.weight.mall.service;

import com.weight.mall.domain.Product;
import com.weight.mall.dto.request.product.PostRequestDto;
import com.weight.mall.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 상품 등록
    public void post(PostRequestDto postRequestDto) {
        System.out.println(postRequestDto.getTitle());
        Product product = postRequestDto.toProduct();
        System.out.println("아아아" + product.getTitle());

        try {
            // 게시글 저장
            productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("상품 등록 중 오류가 발생했습니다.", e);
        }
    }

    // 판매글 찾기
    public Product findById(Long id) {
        // 상품이 존재하는지 확인 -> 상품을 삭제하고 수정 버튼을 누를 경우 처리하기 위함
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("없는 판매글 입니다."));
    }

    // 상품 수정
    public void update(Long productId, PostRequestDto postRequestDto) {
        findById(productId);
        // 수정된 Product 객체 생성
        Product updateProduct = new Product(
                productId,  // 기존 ID 그대로 사용
                postRequestDto.getTitle(),
                postRequestDto.getDescription(),
                postRequestDto.getPrice(),
                postRequestDto.getCategory(),
                postRequestDto.getDate()
        );
        productRepository.save(updateProduct);

    }

    // 상품 삭제
    public void delete(Long productId) {
        // 삭제할 상품이 존재하는지 확인
        Product product = findById(productId);
        productRepository.delete(product);
    }
}
