package com.tamimSoft.fakeStore.service;

import com.tamimSoft.fakeStore.entity.User;
import com.tamimSoft.fakeStore.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAllByRole(String role) {
        List<String> roles = List.of(role);
        return userRepository.findAllByRoles(roles);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    public void deleteByUserName(String username) {
        userRepository.deleteByUserName(username);
    }
}
