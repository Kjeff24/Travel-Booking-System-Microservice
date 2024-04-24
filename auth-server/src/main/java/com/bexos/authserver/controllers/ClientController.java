package com.bexos.authserver.controllers;

import com.bexos.authserver.dto.CreateClientDto;
import com.bexos.authserver.dto.MessageDto;
import com.bexos.authserver.models.Client;
import com.bexos.authserver.repositories.ClientRepository;
import com.bexos.authserver.services.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService clientService;
    private final ClientRepository clientRepository;

    @PostMapping("/create")
    public ResponseEntity<MessageDto> create(@RequestBody CreateClientDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(dto));
    }

    @GetMapping("/getClient/{client-id}")
    public ResponseEntity<Client> findById(@PathVariable("client-id") String id) {
        Client client = clientRepository.findByClientId(id)
                .orElseThrow(() -> new RuntimeException("client not found (findById)"));
        return ResponseEntity.ok(client);
    }
}
