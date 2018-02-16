package com.newspaper.model.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegistrationRequest {

    private String username;
    private String password;
    private String firstname;
    private String lastname;


}