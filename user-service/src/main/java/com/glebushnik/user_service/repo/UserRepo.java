package com.glebushnik.user_service.repo;

import com.glebushnik.user_service.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Transactional
public interface UserRepo extends JpaRepository<User,UUID> {
    Optional<User> findByEmail(String email);
}
