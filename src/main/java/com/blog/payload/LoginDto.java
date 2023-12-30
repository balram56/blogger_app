package com.blog.payload;

import lombok.Data;

@Data
public class LoginDto { //which user is login
    private String usernameOrEmail;
    private String password;
}
