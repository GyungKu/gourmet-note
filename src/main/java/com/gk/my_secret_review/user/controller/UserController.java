package com.gk.my_secret_review.user.controller;

import com.gk.my_secret_review.user.entity.UserEntity;
import com.gk.my_secret_review.user.service.UserService;
import com.gk.my_secret_review.user.vo.LoginUser;
import com.gk.my_secret_review.user.vo.ResponseUser;
import com.gk.my_secret_review.user.vo.UpdateUser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
                .role(userEntity.getRole())
                .build();
        session.setAttribute("login", loginUser);

        ResponseUser responseUser = ResponseUser.builder()
                .username(userEntity.getUsername())
                .build();

        return ResponseEntity.ok(responseUser);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute("login");
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<ResponseUser> myInfo(@AuthenticationPrincipal LoginUser user) {
        ResponseUser response = service.getMyInfo(user.id());

        return ResponseEntity.ok(response);
    }

    @PatchMapping
    public ResponseEntity<ResponseUser> update(@AuthenticationPrincipal LoginUser user,
                                               @Valid @RequestBody UpdateUser update) {

        ResponseUser response = service.update(user.id(), update);
        return ResponseEntity.ok(response);
    }

}
