package com.gk.gourmet_note.user.service;

import com.gk.gourmet_note.common.exception.GlobalException;
import com.gk.gourmet_note.common.naver.NaverService;
import com.gk.gourmet_note.common.naver.NaverUserInfo;
import com.gk.gourmet_note.review.service.ReviewService;
import com.gk.gourmet_note.user.entity.UserEntity;
import com.gk.gourmet_note.user.entity.UserRole;
import com.gk.gourmet_note.user.repository.UserRepository;
import com.gk.gourmet_note.user.vo.RequestLogin;
import com.gk.gourmet_note.user.vo.ResponseMyInfo;
import com.gk.gourmet_note.user.vo.ResponseUser;
import com.gk.gourmet_note.user.vo.UpdateUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository repository;
    private final NaverService naverService;
    private final ReviewService reviewService;
    private final PasswordEncoder passwordEncoder;

    public ResponseUser naverLogin(String code) {
        NaverUserInfo naverUser = naverService.login(code);
        return getOrSaveByNaverUserInfo(naverUser);

    }

    public ResponseUser login(RequestLogin login) {
        UserEntity userEntity = getByEmail(login);
        verifyPassword(login, userEntity);
        return ResponseUser.fromEntity(userEntity, "login");
    }

    public ResponseMyInfo getMyInfo(Long id) {
        UserEntity entity = getById(id);
        return ResponseMyInfo.builder()
                .username(entity.getUsername())
                .email(entity.getEmail())
                .gender(entity.getGender())
                .age(entity.getAge())
                .birthyear(entity.getBirthyear())
                .reviewCount(reviewService.getReviewCountByUserId(id))
                .build();
    }

    public ResponseUser update(Long id, UpdateUser update) {
        UserEntity entity = getById(id);
        entity.update(update.username());

        return ResponseUser.builder()
                .username(update.username())
                .build();
    }

    private ResponseUser getOrSaveByNaverUserInfo(NaverUserInfo naverUser) {
        Optional<UserEntity> byNaverId = repository.findByNaverId(naverUser.id());
        ResponseUser responseUser;
        if (byNaverId == null || byNaverId.isEmpty()) {
            UserEntity userEntity = repository.save(
                    UserEntity.builder()
                            .naverId(naverUser.id())
                            .email(naverUser.email())
                            .gender(naverUser.gender())
                            .birthday(naverUser.birthday())
                            .birthyear(naverUser.birthyear())
                            .age(naverUser.age())
                            .username(UUID.randomUUID().toString())
                            .role(UserRole.USER)
                            .build()
            );
            responseUser = ResponseUser.fromEntity(userEntity, "signup");
        } else {
            responseUser = ResponseUser.fromEntity(byNaverId.get(), "login");
        }
        return responseUser;
    }

    private UserEntity getByEmail(RequestLogin login) {
        return repository.findByEmail(login.email())
                .orElseThrow(() -> {
                    throw new GlobalException("존재하지 않는 회원입니다.", HttpStatus.BAD_REQUEST);
                });
    }

    private void verifyPassword(RequestLogin login, UserEntity userEntity) {
        if (!passwordEncoder.matches(login.password(), userEntity.getPassword())) {
            throw new GlobalException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    private UserEntity getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    throw new GlobalException("존재하지 않는 유저입니다.", HttpStatus.NOT_FOUND);
                });
    }
}
