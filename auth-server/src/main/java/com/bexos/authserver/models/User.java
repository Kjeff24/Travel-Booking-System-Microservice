package com.bexos.authserver.models;

import com.bexos.authserver.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@Builder
@TypeAlias("User")
@Document(value = "Users")
public class User implements UserDetails {
    @Id
    private String id;
    private String fullName;
    @Indexed(unique = true)
    private String username;
    @Indexed(unique = true)
    private String email;
    private String password;
    private Set<Role> roles;
    private String pictureUrl;

    private boolean enabled = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public static User fromOAuth2GithubUser(OAuth2User user){
        Set<Role> roles = CustomerRole();
        return User.builder()
                .email(user.getAttribute("email"))
                .fullName(user.getAttribute("name"))
                .username(user.getAttribute("login"))
                .pictureUrl(user.getAttribute("avatar_url"))
                .roles(roles)
                .enabled(true)
                .build();
    }

    public static User fromOauth2GoogleUser(OAuth2User user){
        Set<Role> roles = CustomerRole();
        return User.builder()
                .email(user.getAttributes().get("email").toString())
                .fullName(user.getAttributes().get("name").toString())
                .username(user.getAttributes().get("given_name").toString())
                .pictureUrl(user.getAttributes().get("picture").toString())
                .roles(roles)
                .enabled(true)
                .build();
    }

    private static Set<Role> CustomerRole() {
        Role customerRole = Role.builder().role(RoleName.CUSTOMER).build();
        Role oauthRole = Role.builder().role(RoleName.OAUTH2).build();
        Set<Role> roles = new HashSet<>();
        roles.add(customerRole);
        roles.add(oauthRole);
        return roles;
    }
}
