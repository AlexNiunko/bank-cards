package com.example.bankcards.service;

import com.example.bankcards.dto.UserPrincipals;

public interface UserJwtService {

    UserPrincipals findByUserId(Long id);

}
