package com.bexos.authserver.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TypeAlias("GoogleUser")
@Document(value = "Google Users")
public class GoogleUser {
    @Id
    private int id;
    private String email;
    private String fullName;
    private String givenName;
    private String familyName;
    private String pictureUrl;

    public static GoogleUser fromOauth2User(OAuth2User user){
        return GoogleUser.builder()
                .email(user.getAttributes().get("email").toString())
                .fullName(user.getAttributes().get("name").toString())
                .givenName(user.getAttributes().get("given_name").toString())
                .familyName(user.getAttributes().get("family_name").toString())
                .pictureUrl(user.getAttributes().get("picture").toString())
                .build();
    }

    @Override
    public String toString() {
        return "GoogleUser{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + fullName + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                '}';
    }

}
