package com.weight.mall.controller;

import com.weight.mall.domain.Product;
import com.weight.mall.dto.request.product.PostRequestDto;
import com.weight.mall.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor // 생성자 주입
public class ProductController {
    private final ProductService productService;

    @GetMapping("/post")
    public String showPostForm(Model model) {
        model.addAttribute("postRequestDto", new PostRequestDto());
        return "post";
    }

    // 판매글 등록
    @PostMapping("/post")
    public String postProduct(@Valid @ModelAttribute PostRequestDto postRequestDto, Model model) {
        try {
            // 상품 등록 실행
            productService.post(postRequestDto);

            // 성공 응답
            model.addAttribute("message", "상품이 등록되었습니다.");
            return "redirect:/"; // 판매글 등록 성공 시 메인 페이지로 이동
        } catch (Exception e) {
            model.addAttribute("error", "상품 등록 중 문제가 발생했습니다.");
            return "post";
        }
    }

    // 판매글 수정 페이지 이동
    @GetMapping("/update/{productId}")
    public String showUpdateForm(@PathVariable Long productId, Model model) {
        // 상품을 조회하여 수정 폼에 해당 데이터를 채워넣음
        try {
            Product product = productService.findById(productId);
            model.addAttribute("postRequestDto", new PostRequestDto(
                    product.getTitle(),
                    product.getDescription(),
                    product.getCategory(),
                    product.getPrice()
            ));
            model.addAttribute("productId", productId);  // 수정하려는 상품의 ID 전달
            return "update";  // 수정 폼 화면으로 이동
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e);
            return "redirect:/"; // 메인 페이지로 리다이렉트
        }
    }

    // 판매글 수정
    @PatchMapping("/update/{productId}")
    public String updateProduct(@PathVariable Long productId, @Valid @ModelAttribute PostRequestDto postRequestDto, Model model) {
        try {
            // 상품 수정 실행
            productService.update(productId, postRequestDto);

            // 성공 응답
            model.addAttribute("message", "상품이 수정되었습니다.");
            return "redirect:/"; // 수정 후 메인 페이지로 리다이렉트
        } catch (Exception e) {
            model.addAttribute("error", "상품 수정 중 문제가 발생했습니다.");
            System.out.println(e.getMessage());
            return "redirect:/"; // 임시로 설정(메인 페이지로 이동)
        }
    }

    // 판매글 삭제
    @DeleteMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable Long productId, Model model) {
        try {
            // 상품 삭제 실행
            productService.delete(productId);

            // 성공 응답
            model.addAttribute("message", "상품이 삭제되었습니다.");
            return "redirect:/";  // 삭제 후 메인 페이지로 리다이렉트
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
            return "redirect:/"; // 임시로 설정(메인 페이지로 이동)
        }
    }
}
