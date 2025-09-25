package com.example.bankcards;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Main {

    public static void main(String[] args) {
        var encoder = new BCryptPasswordEncoder();
        String pass1="123";
        String pass2="456";
        String pass3="789";
        System.out.println(encoder.encode(pass1));
        System.out.println(encoder.encode(pass2));
        System.out.println(encoder.encode(pass3));

    }

}
