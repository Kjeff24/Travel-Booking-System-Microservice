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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client")
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
    public ResponseEntity<?> findById(@PathVariable("client-id") String id) {
        Optional<Client> optionalClient = clientRepository.findByClientId(id);
        if(optionalClient.isPresent()) {
            return ResponseEntity.ok(optionalClient.get());
        }
        return ResponseEntity.badRequest().body("Client not found");
    }

    @GetMapping
    public ResponseEntity<List<Client>> findAll() {
        return ResponseEntity.ok(clientRepository.findAll());
    }
}
