package com.bexos.authserver.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TypeAlias("GitHubUser")
@Document(value = "Github Users")
public class GitHubUser {
    @Id
    private String id;
    private String email;
    private String username;
    private String fullName;
    private String avatarUrl;

    public static GitHubUser fromOAuth2User(OAuth2User user){
        return GitHubUser.builder()
                .email(user.getAttribute("email"))
                .fullName(user.getAttribute("name"))
                .username(user.getAttribute("login"))
                .avatarUrl(user.getAttribute("avatar_url"))
                .build();
    }

    @Override
    public String toString() {
        return "GitHubUser{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", login='" + username + '\'' +
                ", name='" + fullName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
