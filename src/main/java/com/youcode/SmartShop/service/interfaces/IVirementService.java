package com.youcode.SmartShop.service.interfaces;

import com.youcode.SmartShop.dtos.request.VirementCreateRequestDto;
import com.youcode.SmartShop.dtos.response.VirementResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IVirementService {
    VirementResponseDto save(VirementCreateRequestDto  request);
    VirementResponseDto getById(Long  id);
    Page<VirementResponseDto>  getByCommabdeId(Long  id, Pageable pageable);
    Page<VirementResponseDto> getAll(Pageable pageable);
}
