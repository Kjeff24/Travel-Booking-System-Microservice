package com.bexos.authserver.repositories;

import com.bexos.authserver.models.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ClientRepository extends MongoRepository<Client, String> {
    Optional<Client> findByClientId(String clientId);
}
