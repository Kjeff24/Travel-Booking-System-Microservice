package com.bexos.authserver.repositories;

import com.bexos.authserver.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.OptionalInt;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByUsernameIgnoreCase(String username);

    Boolean existsByEmailIgnoreCase(String email);
    Boolean existsByUsernameIgnoreCase(String username);

    User findByPassword(String password);

}
