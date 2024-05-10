package com.bexos.authserver;

import com.bexos.authserver.enums.RoleName;
import com.bexos.authserver.models.Client;
import com.bexos.authserver.models.Role;
import com.bexos.authserver.repositories.ClientRepository;
import com.bexos.authserver.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@RequiredArgsConstructor
public class AuthServerApplication implements CommandLineRunner {
	private final RoleRepository roleRepository;
	private final ClientRepository clientRepository;
	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

	private Client createClient(String clientId, String redirectUrl) {
		Set<ClientAuthenticationMethod> authenticationMethods = new HashSet<>();
		authenticationMethods.add(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
		Set<AuthorizationGrantType> authorizationGrantTypes = new HashSet<>();
		authorizationGrantTypes.add(AuthorizationGrantType.REFRESH_TOKEN);
		authorizationGrantTypes.add(AuthorizationGrantType.AUTHORIZATION_CODE);
		authorizationGrantTypes.add(AuthorizationGrantType.CLIENT_CREDENTIALS);
		Set<String> redirectUris = new HashSet<>();
		redirectUris.add(redirectUrl);
		Set<String> scopes = new HashSet<>();
		scopes.add("openid");
		scopes.add("profile");

		return Client.builder()
				.clientId(clientId)
				.clientSecret(passwordEncoder.encode("secret"))
				.authenticationMethods(authenticationMethods)
				.authorizationGrantTypes(authorizationGrantTypes)
				.redirectUris(redirectUris)
				.scopes(scopes)
				.requireProofKey(true)
				.build();
	}

	public void run(String... args) throws Exception {
		if (!roleRepository.existsByRole(RoleName.ADMIN)) {
			Role adminRole = Role.builder()
					.role(RoleName.ADMIN)
					.build();
			roleRepository.save(adminRole);
		}

		if (!roleRepository.existsByRole(RoleName.CUSTOMER)) {
			Role customeRole = Role.builder()
					.role(RoleName.CUSTOMER)
					.build();
			roleRepository.save(customeRole);
		}

		if (!roleRepository.existsByRole(RoleName.OAUTH2)) {
			Role oauth2Role = Role.builder()
					.role(RoleName.OAUTH2)
					.build();
			roleRepository.save(oauth2Role);
		}

		if(!clientRepository.existsByClientId("angular-client")){
			Client angularClient = createClient("angular-client", "http://127.0.0.1:4200/login/oauth2/code/angular-client");
			clientRepository.save(angularClient);
		}

		if(!clientRepository.existsByClientId("gateway-client")){
			Client gatewayClient = createClient("gateway-client", "http://127.0.0.1:8765/login/oauth2/code/gateway-client");
			clientRepository.save(gatewayClient);
		}

	}
}
