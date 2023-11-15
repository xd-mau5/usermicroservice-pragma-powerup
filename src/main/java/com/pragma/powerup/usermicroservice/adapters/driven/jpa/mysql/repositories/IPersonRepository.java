package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPersonRepository extends JpaRepository<PersonEntity, Long> {
    Optional<PersonEntity> findByDniNumber(Long dniNumber);

    Boolean existsByDniNumber(Long dniNumber);

    boolean existsByMail(String mail);

    Optional<PersonEntity> findByMail(String mail);
}
