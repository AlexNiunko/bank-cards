package com.example.bankcards.entity;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private String firstname;

    private String lastname;

    private LocalDate birthDate;

    private String phoneNumber;
}
