package com.tamimSoft.fakeStore.repository;

import com.tamimSoft.fakeStore.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, Object> {

    User findByUserName(String username);

    void deleteByUserName(String username);

    List<User> findAllByRoles(List<String> roles);
}
