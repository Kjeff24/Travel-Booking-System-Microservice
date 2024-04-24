package com.bexos.authserver.models;

import com.bexos.authserver.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("Role")
@Document(value = "role")
public class Role implements GrantedAuthority {

    @Id
    private String id;
    private RoleName role;

    @Override
    public String getAuthority() {
        return role.name();
    }
}
