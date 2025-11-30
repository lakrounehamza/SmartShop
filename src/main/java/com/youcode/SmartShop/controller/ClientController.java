package com.youcode.SmartShop.controller;

import com.youcode.SmartShop.dtos.request.NiveauFideliteUpdateDto;
import com.youcode.SmartShop.dtos.response.ClientResponseDto;
import com.youcode.SmartShop.dtos.request.ClientCreateRequestDto;
import com.youcode.SmartShop.dtos.response.ClinetStatisticResponseDto;
import com.youcode.SmartShop.dtos.response.CommandeResponseDto;
import com.youcode.SmartShop.service.interfaces.IClientService;
import com.youcode.SmartShop.service.interfaces.ICommandeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/clients")
@AllArgsConstructor
public class ClientController {
    private final IClientService service;
    private final ICommandeService commandeService;

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
    @GetMapping("/{id}/commandes")
    public ResponseEntity<Page<CommandeResponseDto>> getCommandeByClientId(
            @PathVariable Long id ,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<CommandeResponseDto> commandes = commandeService.getByClientId(id,pageable);
        return ResponseEntity.ok(commandes);
    }
    @GetMapping("/{id}/statistic")
    public ResponseEntity<ClinetStatisticResponseDto>  statistic(@PathVariable Long id){
        return ResponseEntity.ok(commandeService.getClientStatistic(id));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteClient(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.ok(Map.of(
                "message", "client supprime avec succes",
                "status", "SUCCESS",
                "id", id
        ));    }
}