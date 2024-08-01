package com.gk.my_secret_review.user.repository;

import com.gk.my_secret_review.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByNaverId(String naverId);

}
