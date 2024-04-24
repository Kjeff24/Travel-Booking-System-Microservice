package com.bexos.authserver.federated;

import java.util.function.Consumer;

import com.bexos.authserver.models.GitHubUser;
import com.bexos.authserver.models.GoogleUser;
import com.bexos.authserver.repositories.GitHubUserRepository;
import com.bexos.authserver.repositories.GoogleUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;

@RequiredArgsConstructor
public final class GithubGoogleUserHandler implements Consumer<OAuth2User> {

    private final GitHubUserRepository githubRepository;
    private final GoogleUserRepository googleRepository;

    @Override
    public void accept(OAuth2User user) {
        // Capture user in a database on first authentication
        if (user.getAttributes().containsKey("login")) {
            if(githubRepository.findByUsername(user.getAttribute("login")).isEmpty()){
                GitHubUser gitHubUser = GitHubUser.fromOAuth2User(user);
                System.out.println(gitHubUser.toString());
                this.githubRepository.save(gitHubUser);
            }
        }
        else if (googleRepository.findByEmail(user.getAttribute("email")).isEmpty()) {
            GoogleUser googleUser = GoogleUser.fromOauth2User(user);
            System.out.println(googleUser.toString());
            this.googleRepository.save(googleUser);
        }
    }


}