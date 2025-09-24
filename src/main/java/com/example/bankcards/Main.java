package com.example.bankcards;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Main {

    public static void main(String[] args) {
        var encoder = new BCryptPasswordEncoder();
        String password1="12345678";
        String password2="87654321";
        System.out.println(encoder.encode(password1));
        System.out.println(encoder.encode(password2));
    }

}
