package com.tamimSoft.store.entity;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("users")
@Hidden
public class User {
    @Id
    private String id;

    @NonNull
    @Indexed(unique = true)
    private String userName;

    private String firstName;
    private String lastName;

    @NonNull
    private String password;

    @NonNull
    @Indexed(unique = true)

    private String email;
    private String phone;
    private Set<String> roles;

    @DBRef
    private Set<Product> products = new HashSet<>();
    @DBRef
    private Set<Cart> carts = new HashSet<>();
    @DBRef
    private Set<Product> favourites = new HashSet<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updateAt;
}
