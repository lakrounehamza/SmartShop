package com.youcode.SmartShop.service.impl;

import com.youcode.SmartShop.entity.CodePromo;
import com.youcode.SmartShop.exception.NotFoundException;
import com.youcode.SmartShop.repository.CodePromoRepository;
import com.youcode.SmartShop.service.interfaces.ICodePromoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CodePromoServiceImpl implements ICodePromoService {

    private final CodePromoRepository codePromoRepository;

    @Override
    public Page<CodePromo> getAll(Pageable pageable) {
        return codePromoRepository.findAll(pageable);
    }

    @Override
    public CodePromo save() {
        CodePromo codePromo = new CodePromo();
        return codePromoRepository.save(codePromo);
    }

    @Override
    public CodePromo getById(long id) {
        return codePromoRepository.findById(id).orElseThrow(()-> new NotFoundException(""));
    }
}
