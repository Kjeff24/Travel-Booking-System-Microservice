package com.bexos.authserver.federated;

import java.util.function.Consumer;

import com.bexos.authserver.models.User;
import com.bexos.authserver.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;

@RequiredArgsConstructor
public final class GithubGoogleUserHandler implements Consumer<OAuth2User> {
    private final UserRepository userRepository;

    @Override
    public void accept(OAuth2User user) {
        String email = user.getAttribute("email");
        String username = user.getAttributes().containsKey("login") ? user.getAttribute("login") : user.getAttribute("given_name");

        if ((email == null || !userRepository.existsByEmailIgnoreCase(email)) && !userRepository.existsByUsernameIgnoreCase(username)) {
            User newUser = user.getAttributes().containsKey("login") ? User.fromOAuth2GithubUser(user) : User.fromOauth2GoogleUser(user);
            System.out.println(newUser.toString());
            this.userRepository.save(newUser);
        }
    }



}