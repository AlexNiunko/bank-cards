package com.example.bankcards.security.authentication;

import com.example.bankcards.security.authentication.dto.UserPrincipals;

public interface UserJwtService {

    UserPrincipals findByUserId(Long id);

}
