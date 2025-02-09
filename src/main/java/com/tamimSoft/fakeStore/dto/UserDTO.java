package com.tamimSoft.fakeStore.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phone;

    private Set<String> roles;
}
