package com.youcode.SmartShop.service.impl;

import com.youcode.SmartShop.dtos.request.ChequeCreateRequestDto;
import com.youcode.SmartShop.dtos.response.ChequeResponseDto;
import com.youcode.SmartShop.entity.Cheque;
import com.youcode.SmartShop.service.interfaces.IChequeService;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;

public class ChequeServiceImpl implements IChequeService {
    @Override
    public Cheque save(ChequeCreateRequestDto request) {
        return null;
    }

    @Override
    public ChequeResponseDto getChequeById(Long id) {
        return null;
    }

    @Override
    public Page<ChequeResponseDto> getAllCheque(Pageable pageable) {
        return null;
    }

    @Override
    public String deleteChequeById(Long id) {
        return "";
    }

    @Override
    public ChequeResponseDto update(ChequeCreateRequestDto request) {
        return null;
    }
}
