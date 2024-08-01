package com.gk.my_secret_review.user.repository;

import com.gk.my_secret_review.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
