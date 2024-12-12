package com.weight.mall.controller;

import com.weight.mall.dto.request.JoinRequestDto;
import com.weight.mall.dto.request.LoginRequestDto;
import com.weight.mall.exception.InvalidCredentialsException;
import com.weight.mall.exception.UserAlreadyExistsException;
import com.weight.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor // 생성자 주입
public class UserController {
    private final UserService userService;

    // 생성자 주입
    // @Autowired
    // private UserController(UserService userService){
    //     this.userService = userService;
    // }

    // 회원가입 페이지
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("joinRequestDto", new JoinRequestDto());
        return "signup"; // `resources/templates/signup.html`를 반환
    }
    
    // 회원가입
    @PostMapping("/signup")
    // public ResponseEntity<String> signup(@Valid @RequestBody JoinRequestDto joinRequestDto) {
    public String signup(@Valid @ModelAttribute JoinRequestDto joinRequestDto, Model model) {
        try {
            // 회원가입 실행
            userService.signup(joinRequestDto);

            // // 성공 응답: 201 Create
            // return ResponseEntity.status(HttpStatus.CREATED)
            //         .body("회원가입이 성공적으로 완료되었습니다.");
            model.addAttribute("message", "회원가입이 성공적으로 완료되었습니다.");
            return "signup-success"; // 회원가입 성공 페이지

        } catch (UserAlreadyExistsException e) {
            // // 이미 존재하는 이메일인 경우: 409 Conflict 상태 코드 반환
            // return ResponseEntity.status(HttpStatus.CONFLICT)
            //         .body(e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "signup"; // 에러 메시지를 포함해 다시 폼으로 이동
        } catch (Exception e) {
            // // 그 외 예외 처리
            // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            //         .body("회원가입 중 문제가 발생했습니다.");
            model.addAttribute("error", "회원가입 중 문제가 발생했습니다.");
            return "signup";
        }
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginRequestDto", new LoginRequestDto());
        return "login"; // `resources/templates/login.html`를 반환
    }

    // 로그인
    @PostMapping("/login")
    // public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
    public String login(@Valid @ModelAttribute LoginRequestDto loginRequestDto, Model model) {
        try {
            // 로그인 실행
            userService.login(loginRequestDto);

            // 성공 응답: 200 OK
            // return ResponseEntity.status(HttpStatus.OK)
            //         .body("로그인 되었습니다.");
            model.addAttribute("message", "로그인이 성공적으로 완료되었습니다.");
            return "login-success"; // 회원가입 성공 페이지
        } catch (InvalidCredentialsException e) {
            // 아이디 or 비밀번호가 잘못된 경우: 401 Unauthorized
            // return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            //         .body(e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "login";
        } catch (Exception e) {
            // return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            //         .body("로그인에 실패했습니다.");
            model.addAttribute("error", "로그인 중 문제가 발생했습니다.");
            return "login";
                    
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@Valid @PathVariable Long id) {
        // 회원탈퇴 실행: 로그인 상태에서 탈퇴를 진행하기 때문에 예외 처리 하지 않음
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("회원탈퇴 되었습니다.");
    }
}
