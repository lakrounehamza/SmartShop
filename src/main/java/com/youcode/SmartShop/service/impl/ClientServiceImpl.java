package com.youcode.SmartShop.service.impl;

import com.youcode.SmartShop.dtos.request.ClientCreateRequestDto;
import com.youcode.SmartShop.dtos.response.ClientResponseDto;
import com.youcode.SmartShop.entity.Client;
import com.youcode.SmartShop.entity.User;
import com.youcode.SmartShop.exception.DuplicateClientException;
import com.youcode.SmartShop.mapper.ClientMapper;
import com.youcode.SmartShop.mapper.UserMapper;
import com.youcode.SmartShop.repository.ClientRepository;
import com.youcode.SmartShop.repository.UserRepository;
import com.youcode.SmartShop.service.interfaces.IClientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final UserRepository  userRepository;
    private final ClientRepository clientRepository;
    private final ClientMapper  clientMapper;
    private  final UserMapper  userMapper;

    @Override
    public ClientResponseDto save(@Valid ClientCreateRequestDto request) {
        if(userRepository.existsByUsername(request.user().username()))
            throw  new DuplicateClientException("Un client avec ce nom d'user existe deja");
        if(clientRepository.existsByEmail(request.email()))
            throw new DuplicateClientException("Un client avec cet email existe deja");
        Client client  =  clientMapper.toEntity(request);
        User user  =  userMapper.toEntity(request.user());
        User userSaved = userRepository.save(user);
        client.setUser(userSaved);
        Client  clientSaved = clientRepository.save(client);
        return    clientMapper.toDTO(clientSaved);
    }
}
