package com.example.bankcards.repository;

import com.example.bankcards.entity.Users;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {

    Optional<Users> findByLogin(String login);

}
