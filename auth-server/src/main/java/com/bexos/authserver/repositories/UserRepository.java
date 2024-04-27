package com.bexos.authserver.repositories;

import com.bexos.authserver.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.OptionalInt;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);
}
