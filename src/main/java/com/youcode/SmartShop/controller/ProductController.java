package com.youcode.SmartShop.controller;

import com.youcode.SmartShop.dtos.request.ProduitCreateRequestDto;
import com.youcode.SmartShop.dtos.request.StockProduitUpdateRequestDto;
import com.youcode.SmartShop.dtos.response.ProduitResponseDto;
import com.youcode.SmartShop.service.interfaces.IProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/products")
@AllArgsConstructor
public class ProductController {

    private final IProductService productService;

    @PostMapping
    public ResponseEntity<ProduitResponseDto>  createProduct(@Valid @RequestBody ProduitCreateRequestDto requst){
        ProduitResponseDto response = productService.save(requst);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PatchMapping("{id}")
    public ResponseEntity<ProduitResponseDto>  upadateStock(@Valid @RequestBody StockProduitUpdateRequestDto stock , @PathVariable Long id){
        ProduitResponseDto response = productService.updateStock(id,stock);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping
    public ResponseEntity<Page<ProduitResponseDto>>  getAll(@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAll(pageable));
    }
    @GetMapping("{id}")
    public ResponseEntity<ProduitResponseDto> getById(@PathVariable Long  id){
        ProduitResponseDto response = productService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}