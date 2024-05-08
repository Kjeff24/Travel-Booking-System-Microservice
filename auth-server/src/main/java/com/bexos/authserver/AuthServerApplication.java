package com.bexos.authserver;

import com.bexos.authserver.enums.RoleName;
import com.bexos.authserver.models.Role;
import com.bexos.authserver.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class AuthServerApplication implements CommandLineRunner {
	private final RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
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

	}
}
