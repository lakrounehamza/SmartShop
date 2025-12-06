package com.youcode.SmartShop.service;

import com.youcode.SmartShop.dtos.request.ProduitCreateRequestDto;
import com.youcode.SmartShop.dtos.response.ProduitResponseDto;
import com.youcode.SmartShop.entity.Product;
import com.youcode.SmartShop.mapper.ProductMapperImpl;
import com.youcode.SmartShop.repository.ProductRepository;
import com.youcode.SmartShop.service.impl.ProduitServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
}
