package com.tamimSoft.fakeStore.service;

import com.tamimSoft.fakeStore.dto.SignUpDTO;
import com.tamimSoft.fakeStore.dto.UserDTO;
import com.tamimSoft.fakeStore.entity.User;
import com.tamimSoft.fakeStore.exception.ResourceNotFoundException;
import com.tamimSoft.fakeStore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public Page<UserDTO> getAllUserDTOs(Pageable pageable) {
        return userRepository.findAll(pageable).map(user -> new UserDTO(user.getId(), user.getUserName(), user.getFirstName(), user.getLastName(), null, // Do not expose password
                user.getEmail(), user.getPhone(), user.getRoles()));
    }

    public Page<UserDTO> getAllUserDTOsByRole(String role, Pageable pageable) {
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Role cannot be null or empty");
        }
        return userRepository.findAllByRolesContaining(role, pageable).map(user -> new UserDTO(user.getId(), user.getUserName(), user.getFirstName(), user.getLastName(), null, // Do not expose password
                user.getEmail(), user.getPhone(), user.getRoles()));
    }

    public void createUser(SignUpDTO signUpDTO) {
        User user = new User();
        user.setUserName(signUpDTO.getUserName());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        user.setEmail(signUpDTO.getEmail());
        user.setPhone(signUpDTO.getPhone());
        user.setRoles(Set.of("CUSTOMER"));
        userRepository.save(user);
    }

    public void updateUserProfileInfo(UserDTO userDTO, String userName) {
        User user = arrangeUserInfo(userDTO, userName);
        userRepository.save(user);
    }

    public void updateUserInfoWithRole(UserDTO userDTO, String userName) {
        User user = arrangeUserInfo(userDTO, userName);
        Set<String> roles = user.getRoles();
        if (userDTO.getRoles() != null) {
            roles.addAll(userDTO.getRoles());
        }
        user.setRoles(roles);
        userRepository.save(user);
    }

    private User arrangeUserInfo(UserDTO userDTO, String userName) {
        User user = getUserByUserName(userName);
        user.setFirstName(userDTO.getFirstName() != null ? userDTO.getFirstName() : user.getFirstName());
        user.setLastName(userDTO.getLastName() != null ? userDTO.getLastName() : user.getLastName());
        user.setPassword(userDTO.getPassword() != null ? userDTO.getPassword() : user.getPassword());
        user.setPhone(userDTO.getPhone() != null ? userDTO.getPhone() : user.getPhone());
        return user;
    }

    public User getUserByUserName(String username) {
        return userRepository.findByUserName(username).orElseThrow(() -> new ResourceNotFoundException("User not found with customer name: " + username));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with customer email: " + email));
    }

    public void deleteUserByUserName(String username) {
        if (!userRepository.existsByUserName(username)) {
            throw new ResourceNotFoundException("User not found with customer name: " + username);
        }
        userRepository.deleteByUserName(username);
    }

    public UserDTO getUserDTO(String username) {
        User user = getUserByUserName(username);
        return new UserDTO(user.getId(), user.getUserName(), user.getFirstName(), user.getLastName(), null, // Do not expose password
                user.getEmail(), user.getPhone(), user.getRoles());
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }
}
