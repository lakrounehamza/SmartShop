package com.youcode.SmartShop.service.interfaces;

import com.youcode.SmartShop.entity.CodePromo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICodePromoService {
    Page<CodePromo>  getAll(Pageable pageable);
    CodePromo save();
    CodePromo   getById(long id);
}
