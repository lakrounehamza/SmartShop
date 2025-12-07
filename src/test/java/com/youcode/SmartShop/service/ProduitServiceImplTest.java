package com.youcode.SmartShop.service;

import com.youcode.SmartShop.dtos.request.ProduitCreateRequestDto;
import com.youcode.SmartShop.dtos.request.StockProduitUpdateRequestDto;
import com.youcode.SmartShop.dtos.response.ProduitResponseDto;
import com.youcode.SmartShop.entity.Product;
import com.youcode.SmartShop.exception.NotFoundException;
import com.youcode.SmartShop.mapper.ProductMapperImpl;
import com.youcode.SmartShop.repository.ProductRepository;
import com.youcode.SmartShop.service.impl.ProduitServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class ProduitServiceImplTest {

    @Mock
    private ProductMapperImpl productMapper;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProduitServiceImpl   produitService;
    private Product  product;
    private ProduitResponseDto   responseDto;
    private ProduitCreateRequestDto  requestDto;

    @BeforeEach
    public void setUp(){
        product  = new Product(1L,"name", BigDecimal.valueOf(12),1);
        requestDto = new ProduitCreateRequestDto("name",BigDecimal.valueOf(12),1);
        responseDto  =  new ProduitResponseDto(1L,"name",BigDecimal.valueOf(12),1);
    }

    @Test
    public  void  SavedTest(){
    when(productMapper.toEntity(requestDto)).thenReturn(product);
    when(productRepository.save(any(Product.class))).thenReturn(product);
    when(productMapper.toDTO(product)).thenReturn(responseDto);

    ProduitResponseDto productSaved  =  produitService.save(requestDto);

    assertNotNull(productSaved);

    assertEquals(productSaved.id(),responseDto.id());

        verify(productRepository).save(any(Product.class));

    }
    @Test
    public void updateStockTest() {
        StockProduitUpdateRequestDto requestDto1 = new StockProduitUpdateRequestDto(12);

        Product updatedProduct = new Product(1L, "name", BigDecimal.valueOf(12), 12);
        ProduitResponseDto updatedResponse = new ProduitResponseDto(1L, "name", BigDecimal.valueOf(12), 12);

        when(productRepository.existsById(1L)).thenReturn(true);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(updatedProduct);
        when(productMapper.toDTO(updatedProduct)).thenReturn(updatedResponse);

        ProduitResponseDto result = produitService.updateStock(1L, requestDto1);

        assertNotNull(result);
        assertEquals(12, product.getStock());
        assertEquals(12, result.stock());

        verify(productRepository).existsById(1L);
        verify(productRepository).findById(1L);
        verify(productRepository).save(product);
    }
    @Test
    public void updateStock_NotFoundTest() {
        when(productRepository.existsById(5L)).thenReturn(false);

        assertThrows(NotFoundException.class, () ->
                produitService.updateStock(5L, new StockProduitUpdateRequestDto(10))
        );

        verify(productRepository).existsById(5L);
    }

}
