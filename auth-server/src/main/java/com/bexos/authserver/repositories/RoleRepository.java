package com.bexos.authserver.repositories;

import com.bexos.authserver.enums.RoleName;
import com.bexos.authserver.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByRole(RoleName roleName);

    boolean existsByRole(RoleName roleName);
}
