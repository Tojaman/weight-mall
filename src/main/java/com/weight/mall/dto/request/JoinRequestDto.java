package com.weight.mall.dto.request;

import com.weight.mall.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequestDto {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;

    public JoinRequestDto() {}

    // 완성된 생성자
    public JoinRequestDto(String email, String password, String name, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // DTO를 User 엔티티로 변환
    public User toUser() {
        return new User(email, password, name, phoneNumber);
    }
}