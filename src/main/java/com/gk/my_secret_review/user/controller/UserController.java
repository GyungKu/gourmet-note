package com.gk.my_secret_review.user.controller;

import com.gk.my_secret_review.user.entity.UserEntity;
import com.gk.my_secret_review.user.service.UserService;
import com.gk.my_secret_review.user.vo.LoginUser;
import com.gk.my_secret_review.user.vo.ResponseUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    private final UserService service;

    @GetMapping("/naver")
    public ResponseEntity<ResponseUser> naverLogin(String code, HttpServletRequest request) {
        UserEntity userEntity = service.naverLogin(code);

        HttpSession session = request.getSession();
        LoginUser loginUser = LoginUser.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .build();
        session.setAttribute("login", loginUser);

        ResponseUser responseUser = ResponseUser.builder()
                .username(userEntity.getUsername())
                .build();

        return ResponseEntity.ok(responseUser);
    }

}
