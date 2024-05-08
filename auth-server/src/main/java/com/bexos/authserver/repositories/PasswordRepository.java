package com.bexos.authserver.repositories;

import com.bexos.authserver.models.Password;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PasswordRepository extends MongoRepository<Password, String> {
    Password findByUserId(String userId);
    List<Password> findAllByUserId(String userId);
}
