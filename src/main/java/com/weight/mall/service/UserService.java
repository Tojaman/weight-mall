package com.weight.mall.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weight.mall.domain.User;
import com.weight.mall.dto.request.JoinRequestDto;
import com.weight.mall.dto.request.LoginRequestDto;
import com.weight.mall.exception.InvalidCredentialsException;
import com.weight.mall.exception.UserAlreadyExistsException;
import com.weight.mall.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
//@RequiredArgsConstructor // final로 선언된 필드들을 대상으로 생성자 자동 생성
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signup(JoinRequestDto joinRequestDto) {
        // 이메일 존재 여부 확인 -> 이미 존재하는 이메일일 경우 오류 throw
        logger.info("이메일: {}", joinRequestDto.getEmail());
        userRepository.findByEmail(joinRequestDto.getEmail())
                        .ifPresent(user -> {
                            throw new UserAlreadyExistsException("이미 존재하는 이메일입니다.");
                        });

        // 문제가 없다면 DB에 저장
        User user = joinRequestDto.toUser();
        userRepository.save(user);
    }

    public void login(LoginRequestDto loginRequestDto) {
        // 이메일, 비밀번호 확인 -> 문제 발생시 오류 throw
        userRepository.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword())
                .orElseThrow(() -> new InvalidCredentialsException("이메일 또는 비밀번호가 잘못 되었습니다."));
    }

    public void delete(Long id) {
        // 회원탈퇴
        // 로그인 상태에서 회원탈퇴를 하기 때문에 예외처리 하지 않음
        Optional<User> user = userRepository.findById(id);

        // optionalUser가 존재하는지 체크하고, 존재하면 삭제
//        user.ifPresent(user -> userRepository.delete(user)); // 람다식
        user.ifPresent(userRepository::delete); // 메서드 레퍼런스
    }
}
