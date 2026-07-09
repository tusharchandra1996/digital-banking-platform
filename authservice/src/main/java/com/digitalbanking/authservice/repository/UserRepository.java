package com.digitalbanking.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalbanking.authservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

	boolean existsByMobile(String mobile);

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);
}
