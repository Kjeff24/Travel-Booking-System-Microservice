package com.bexos.authserver.repositories;

import com.bexos.authserver.models.GoogleUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GoogleUserRepository extends MongoRepository<GoogleUser, String> {
    Optional<GoogleUser> findByEmail(String email);
}
