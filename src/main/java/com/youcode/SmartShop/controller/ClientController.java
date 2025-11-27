package com.youcode.SmartShop.controller;

import com.youcode.SmartShop.dtos.request.NiveauFideliteUpdateDto;
import com.youcode.SmartShop.dtos.response.ClientResponseDto;
import com.youcode.SmartShop.dtos.request.ClientCreateRequestDto;
import com.youcode.SmartShop.service.interfaces.IClientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/clients")
@AllArgsConstructor
public class ClientController {
    private final IClientService service;

    @PostMapping
    public ResponseEntity<ClientResponseDto> register(@Valid @RequestBody ClientCreateRequestDto request) {
        ClientResponseDto response = service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDto> getClientById(@PathVariable Long id) {
        ClientResponseDto client = service.getClientById(id);
        return ResponseEntity.ok(client);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientResponseDto> updateNiveauFidelite(@PathVariable Long id, @RequestBody @Valid NiveauFideliteUpdateDto request) {
        return ResponseEntity.ok().body(service.updateNiveauFidelite(id, request));
    }

    @GetMapping
    public ResponseEntity<Page<ClientResponseDto>> getAllClients(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ClientResponseDto> clients = service.getAll(pageable);
        return ResponseEntity.ok(clients);
    }

}
