package com.carfix.carfixrwanda.repository;

import com.carfix.carfixrwanda.enums.Role;
import com.carfix.carfixrwanda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);
}