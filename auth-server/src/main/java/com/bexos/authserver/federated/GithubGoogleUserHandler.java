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
        // Capture user in a database on first authentication
        if (user.getAttributes().containsKey("login")) {
            if(userRepository.findByEmailIgnoreCase(user.getAttribute("login")).isEmpty() && !userRepository.existsByEmailIgnoreCase(user.getAttribute("email"))){
                User gitHubUser = User.fromOAuth2GithubUser(user);
                System.out.println(gitHubUser.toString());
                this.userRepository.save(gitHubUser);
            }
        }
        else if (userRepository.findByEmailIgnoreCase(user.getAttribute("email")).isEmpty() && !userRepository.existsByUsernameIgnoreCase(user.getAttribute("email"))) {
            User googleUser = User.fromOauth2GoogleUser(user);
            System.out.println(googleUser.toString());
            this.userRepository.save(googleUser);
        }
    }


}