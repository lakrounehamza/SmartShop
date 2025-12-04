package com.youcode.SmartShop.controller;

import com.youcode.SmartShop.entity.CodePromo;
import com.youcode.SmartShop.service.interfaces.ICodePromoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/codepromo")
public class CodePromoController {

    private final ICodePromoService codePromoService;

    @PostMapping
    public ResponseEntity<CodePromo>  create(){
        return  ResponseEntity.status(HttpStatus.CREATED).body(codePromoService.save());
    }
    @GetMapping
    public ResponseEntity<Page<CodePromo>> getAll(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable p) {
        return ResponseEntity.ok(codePromoService.getAll(p));
    }

    @GetMapping("{id}")
    public ResponseEntity<CodePromo>  getById(@PathVariable  Long id){
        return  ResponseEntity.status(HttpStatus.OK).body(codePromoService.getById(id));
    }
}
