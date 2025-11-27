package com.youcode.SmartShop.controller;

import com.youcode.SmartShop.dtos.request.CommandeCreateRequestDto;
import com.youcode.SmartShop.dtos.response.CommandeResponseDto;
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

@RestController
@RequestMapping("/api/commandes")
@AllArgsConstructor
public class CommandeController {
    private final ICommandeService service;

    @PostMapping
    public ResponseEntity<CommandeResponseDto> save(@Valid @RequestBody CommandeCreateRequestDto request) {
        CommandeResponseDto response = service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<CommandeResponseDto> getById(@PathVariable Long id) {
        CommandeResponseDto response = service.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<Page<CommandeResponseDto>> getByClientId(
            @PathVariable Long id,
            @PageableDefault(size = 10, sort = "date", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<CommandeResponseDto> response = service.getByClientId(id, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<CommandeResponseDto>> getAll(
            @PathVariable Long id,
            @PageableDefault(size = 10, sort = "date", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<CommandeResponseDto> response = service.getAll(pageable);
        return ResponseEntity.ok(response);
    }

}
