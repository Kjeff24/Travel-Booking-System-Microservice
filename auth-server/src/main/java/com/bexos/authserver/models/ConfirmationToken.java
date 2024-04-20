package com.bexos.authserver.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@TypeAlias("ConfirmationToken")
@Document(value = "confirmationToken")
public class ConfirmationToken {
    @Id
    private String id;
    private String confirmationToken;
    @Builder.Default
    private final LocalDateTime date = LocalDateTime.now();

    @DBRef
    private User user;

}
