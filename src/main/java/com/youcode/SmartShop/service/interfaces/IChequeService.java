package com.youcode.SmartShop.service.interfaces;

import com.youcode.SmartShop.dtos.request.ChequeCreateRequestDto;
import com.youcode.SmartShop.dtos.response.ChequeResponseDto;
import com.youcode.SmartShop.entity.Cheque;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IChequeService {
    ChequeResponseDto  save(ChequeCreateRequestDto request);
    ChequeResponseDto   getChequeById(Long  id);
    Page<ChequeResponseDto> getAllCheque(Pageable  pageable);
    String   deleteChequeById(Long id);
}
