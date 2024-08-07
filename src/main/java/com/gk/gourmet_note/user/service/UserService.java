package com.gk.gourmet_note.user.service;

import com.gk.gourmet_note.common.exception.GlobalException;
import com.gk.gourmet_note.common.naver.NaverService;
import com.gk.gourmet_note.common.naver.NaverUserInfo;
import com.gk.gourmet_note.review.service.ReviewService;
import com.gk.gourmet_note.user.entity.UserEntity;
import com.gk.gourmet_note.user.entity.UserRole;
import com.gk.gourmet_note.user.repository.UserRepository;
import com.gk.gourmet_note.user.vo.ResponseUser;
import com.gk.gourmet_note.user.vo.UpdateUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository repository;
    private final NaverService naverService;
    private final ReviewService reviewService;

    public UserEntity naverLogin(String code) {
        NaverUserInfo naverUser = naverService.login(code);
        return getOrSaveByNaverUserInfo(naverUser);

    }

    public ResponseUser getMyInfo(Long id) {
        UserEntity entity = getById(id);
        return ResponseUser.builder()
                .username(entity.getUsername())
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

    private UserEntity getOrSaveByNaverUserInfo(NaverUserInfo naverUser) {
        return repository.findByNaverId(naverUser.id()).orElseGet(() ->
                repository.save(
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
                )
        );
    }

    private UserEntity getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    throw new GlobalException("존재하지 않는 유저입니다.", HttpStatus.NOT_FOUND);
                });
    }
}
