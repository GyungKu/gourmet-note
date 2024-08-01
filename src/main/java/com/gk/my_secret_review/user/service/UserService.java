package com.gk.my_secret_review.user.service;

import com.gk.my_secret_review.common.naver.NaverService;
import com.gk.my_secret_review.common.naver.NaverUserInfo;
import com.gk.my_secret_review.user.entity.UserEntity;
import com.gk.my_secret_review.user.entity.UserRole;
import com.gk.my_secret_review.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;
    private final NaverService naverService;

    public UserEntity naverLogin(String code) {
        NaverUserInfo naverUser = naverService.login(code);
        return getOrSaveByNaverUserInfo(naverUser);
    }

    private UserEntity getOrSaveByNaverUserInfo(NaverUserInfo naverUser) {
        return repository.findByNaverId(naverUser.id())
                .orElse(repository
                        .save(UserEntity.builder()
                                .naverId(naverUser.id())
                                .email(naverUser.email())
                                .gender(naverUser.gender())
                                .birthday(naverUser.birthday())
                                .birthyear(naverUser.birthyear())
                                .age(naverUser.age())
                                .username(UUID.randomUUID().toString())
                                .role(UserRole.USER)
                                .build()));
    }
}
