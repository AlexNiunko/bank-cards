package com.example.bankcards.repository;

import com.example.bankcards.entity.RequestCardBlock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestCardBlockRepository extends CrudRepository<RequestCardBlock,Long> {

}
