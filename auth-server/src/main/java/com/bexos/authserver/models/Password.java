package com.bexos.authserver.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@TypeAlias("Password")
@Document(value = "Passwords")
public class Password {
    @Id
    private String id;
    private String userId;
    private List<String> userPasswords;
}
