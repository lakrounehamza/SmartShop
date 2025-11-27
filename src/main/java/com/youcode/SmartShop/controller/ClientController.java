package com.youcode.SmartShop.controller;

import com.youcode.SmartShop.dtos.response.ClientResponseDto;
import com.youcode.SmartShop.dtos.request.ClientCreateRequestDto;
import com.youcode.SmartShop.service.interfaces.IClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/clients")
@AllArgsConstructor
public class ClientController {
    private final IClientService service;

    @PostMapping
    public ResponseEntity<ClientResponseDto> register(@RequestBody ClientCreateRequestDto request) {
        ClientResponseDto response = service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
