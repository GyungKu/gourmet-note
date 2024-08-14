package com.gk.gourmet_note.user.controller;

import com.gk.gourmet_note.user.service.UserService;
import com.gk.gourmet_note.user.vo.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        ResponseUser responseUser = service.naverLogin(code);

        HttpSession session = request.getSession();
        LoginUser loginUser = LoginUser.builder()
                .id(responseUser.id())
                .username(responseUser.username())
                .role(responseUser.role())
                .build();
        session.setAttribute("login", loginUser);

        return ResponseEntity.ok(responseUser);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseUser> login(@RequestBody RequestLogin login, HttpServletRequest request) {
        ResponseUser responseUser = service.login(login);

        HttpSession session = request.getSession();
        LoginUser loginUser = LoginUser.builder()
                .id(responseUser.id())
                .username(responseUser.username())
                .role(responseUser.role())
                .build();
        session.setAttribute("login", loginUser);

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
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseMyInfo> myInfo(@AuthenticationPrincipal LoginUser user) {
        ResponseMyInfo response = service.getMyInfo(user.id());

        return ResponseEntity.ok(response);
    }

    @PatchMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseUser> update(@AuthenticationPrincipal LoginUser user,
                                               @Valid @RequestBody UpdateUser update) {

        ResponseUser response = service.update(user.id(), update);
        return ResponseEntity.ok(response);
    }

}
