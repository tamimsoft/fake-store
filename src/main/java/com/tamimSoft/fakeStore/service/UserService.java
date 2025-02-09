package com.tamimSoft.fakeStore.service;

import com.tamimSoft.fakeStore.entity.User;
import com.tamimSoft.fakeStore.exception.ResourceNotFoundException;
import com.tamimSoft.fakeStore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Retrieves all users from the database.
     *
     * @param pageable The pageable object containing the page and size information.
     * @return A Page object containing the list of users.
     */
    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    /**
     * Retrieve all users with a specific role.
     *
     * @param role The role to filter users by.
     * @return A Page object containing the list of users with the specified role.
     */
    public Page<User> findAllUsersByRole(String role, Pageable pageable) {
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Role cannot be null or empty");
        }
        return userRepository.findAllByRolesContaining(role, pageable);
    }


    /**
     * Saves a new user to the database.
     *
     * @param user The user to be saved.
     * @return The saved user.
     */
    public User saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user The user to be updated.
     * @return The updated user.
     */
    public User updateUser(User user) {
        return userRepository.save(user);
    }


    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to be found.
     * @return The user with the specified username.
     */
    public User findByUserName(String username) {
        return userRepository.findByUserName(username);
    }


    /**
     * Delete a user by their username.
     *
     * @param username The username of the user to be deleted.
     */
    public void deleteByUserName(String username) {
        if (!userRepository.existsByUserName(username)) {
            throw new ResourceNotFoundException("User not found with user name: " + username);
        }
        userRepository.deleteByUserName(username);
    }

}
