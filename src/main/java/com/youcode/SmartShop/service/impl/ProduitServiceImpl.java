package com.youcode.SmartShop.service.impl;

import com.youcode.SmartShop.dtos.request.ProduitCreateRequestDto;
import com.youcode.SmartShop.dtos.request.StockProduitUpdateRequestDto;
import com.youcode.SmartShop.dtos.response.ProduitResponseDto;
import com.youcode.SmartShop.entity.Product;
import com.youcode.SmartShop.exception.NotFoundException;
import com.youcode.SmartShop.mapper.ProductMapper;
import com.youcode.SmartShop.repository.ProductRepository;
import com.youcode.SmartShop.service.interfaces.IProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ProduitServiceImpl implements IProductService {
    private  final ProductMapper  productMapper;
    private final ProductRepository  productRepository;
    @Override
    public ProduitResponseDto save(ProduitCreateRequestDto request) {
        Product  product = productMapper.toEntity(request);
        Product  produitSaved = productRepository.save(product);
        return productMapper.toDTO(produitSaved);
    }

    @Override
    public ProduitResponseDto updateStock(Long id, StockProduitUpdateRequestDto request) {
        if(!productRepository.existsById(id))
            throw  new NotFoundException("aucun produit trouve");
        Product product = productRepository.findById(id).get();
        product.setStock(request.stock());
        productRepository.save(product);
        return productMapper.toDTO(product);
    }

    @Override
    public Page<ProduitResponseDto> getAll(Pageable pageable) {
        Page<Product> products  = productRepository.findByDeletedFalse(pageable);
        if(products.getTotalElements()<1)
            throw  new NotFoundException("aucun produit trouve");
        return products.map(productMapper::toDTO);
    }

    @Override
    public ProduitResponseDto getById(Long id) {
        return productMapper.toDTO(productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("produit introuvable avec l'id" + id)));
    }
    @Override
    public Product delete(Long id){
        Optional<Product>   productOptional = productRepository.findByIdAndDeletedFalse(id);
        if(productOptional.isPresent()){
            Product product = productOptional.get();
            product.setDeleted(false);
            productRepository.save(product);
            return  product;
        }
        throw new NotFoundException("produit introuvable avec l'id" + id);
    }

}
