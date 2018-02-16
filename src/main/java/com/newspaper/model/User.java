package com.newspaper.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {

    private Integer id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private List<Role> roles;


}