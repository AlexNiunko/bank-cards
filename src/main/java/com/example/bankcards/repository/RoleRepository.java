package com.example.bankcards.repository;

import com.example.bankcards.entity.Role;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    Optional<Role> findByName(String name);

}
