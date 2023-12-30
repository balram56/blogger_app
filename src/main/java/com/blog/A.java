package com.blog;

import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class A {
    public static void main(String[] args) {
        //password encoded -> In spring security  should be used encoded password
       PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("testing"));
    }
}
