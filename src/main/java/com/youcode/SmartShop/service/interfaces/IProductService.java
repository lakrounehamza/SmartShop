package com.youcode.SmartShop.service.interfaces;

import com.youcode.SmartShop.dtos.request.ProduitCreateRequestDto;
import com.youcode.SmartShop.dtos.request.StockProduitUpdateRequestDto;
import com.youcode.SmartShop.dtos.response.ProduitResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    ProduitResponseDto save(ProduitCreateRequestDto request);
    ProduitResponseDto updateStock(Long id, StockProduitUpdateRequestDto request);
    Page<ProduitResponseDto> getAll(Pageable pageable);
    ProduitResponseDto getById(Long id);
}
