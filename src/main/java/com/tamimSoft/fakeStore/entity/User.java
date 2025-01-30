package com.tamimSoft.fakeStore.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document("users")
public class User {
    @Id
    private Object id;
    @NonNull
    @Indexed(unique = true)
    private String userName;
    @NonNull
    private String password;
    private String email;
    private String phone;
    private List<String> roles;
    private String token;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
}
