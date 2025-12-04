package com.youcode.SmartShop.service.impl;

import com.youcode.SmartShop.dtos.request.ClientCreateRequestDto;
import com.youcode.SmartShop.dtos.request.NiveauFideliteUpdateDto;
import com.youcode.SmartShop.dtos.response.ClientResponseDto;
import com.youcode.SmartShop.entity.Client;
import com.youcode.SmartShop.entity.User;
import com.youcode.SmartShop.enums.CustomerTier;
import com.youcode.SmartShop.exception.DuplicateClientException;
import com.youcode.SmartShop.exception.NotFoundException;
import com.youcode.SmartShop.mapper.ClientMapper;
import com.youcode.SmartShop.mapper.UserMapper;
import com.youcode.SmartShop.repository.ClientRepository;
import com.youcode.SmartShop.repository.UserRepository;
import com.youcode.SmartShop.service.interfaces.IClientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final UserMapper userMapper;

    @Override
    public ClientResponseDto save(@Valid ClientCreateRequestDto request) {
        if (userRepository.existsByUsername(request.user().username()))
            throw new DuplicateClientException("Un client avec ce nom d'user existe deja");
        if (clientRepository.existsByEmail(request.email()))
            throw new DuplicateClientException("Un client avec cet email existe deja");
        Client client = clientMapper.toEntity(request);
        User user = userMapper.toEntity(request.user());
        String hashedPassword = BCrypt.hashpw(request.user().password(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        User userSaved = userRepository.save(user);
        client.setUser(userSaved);
        client.setNiveauFidelite(CustomerTier.BASIC);
        Client clientSaved = clientRepository.save(client);
        return clientMapper.toDTO(clientSaved);
    }

    @Override
    public ClientResponseDto getClientById(Long id) {
        return clientMapper.toDTO(clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("client introuvable avec l'id" + id)));
    }

    @Override
    public ClientResponseDto updateNiveauFidelite(Long id, NiveauFideliteUpdateDto request) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ne trouvez le client avec l'id " + id + " pour modification"));
        client.setNiveauFidelite(request.niveauFidelite());
        clientRepository.save(client);
        return clientMapper.toDTO(client);
    }

    @Override
    public Page<ClientResponseDto> getAll(Pageable pageable) {
        Page<ClientResponseDto> cliens = clientRepository.findAll(pageable).map(clientMapper::toDTO);
        if(cliens.getTotalElements()<1)
            throw   new NotFoundException("Aucun client trouve");
        return cliens;
    }
    @Override
    public void deleteById(Long  id){
        if(!clientRepository.existsById(id))
            throw new NotFoundException("client introuvable avec l'id "+id);
        clientRepository.deleteById(id);
    }

}
