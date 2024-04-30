package com.bexos.authserver.repositories;

import com.bexos.authserver.models.Password;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PasswordRepository extends MongoRepository<Password, String> {
    Password findByUserId(String username);
}
