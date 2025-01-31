package com.tamimSoft.fakeStore.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserRoleDTO {
    private String userName;
    private List<String> roles;
}
