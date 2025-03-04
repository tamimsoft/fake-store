package com.tamimSoft.fakeStore.repository;

import com.tamimSoft.fakeStore.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUserName(String username);

    Optional<User> findByEmail(String email);

    void deleteByUserName(String username);

    Page<User> findAllByRolesContaining(String role, Pageable pageable);

    boolean existsByUserName(String username);
}

