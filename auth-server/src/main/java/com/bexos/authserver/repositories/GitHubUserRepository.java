package com.bexos.authserver.repositories;

import com.bexos.authserver.models.GitHubUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GitHubUserRepository extends MongoRepository<GitHubUser, String> {
    Optional<GitHubUser> findByUsername(String username);
}
