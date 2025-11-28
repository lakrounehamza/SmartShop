package com.youcode.SmartShop.service.interfaces;

import com.youcode.SmartShop.dtos.request.ClientCreateRequestDto;
import com.youcode.SmartShop.dtos.request.NiveauFideliteUpdateDto;
import com.youcode.SmartShop.dtos.response.ClientResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClientService {
    ClientResponseDto save(ClientCreateRequestDto request);
    ClientResponseDto getClientById(Long id);
    ClientResponseDto updateNiveauFidelite(Long  id, NiveauFideliteUpdateDto request);
    Page<ClientResponseDto>  getAll(Pageable pageable);
    void deleteById(Long id);
}
