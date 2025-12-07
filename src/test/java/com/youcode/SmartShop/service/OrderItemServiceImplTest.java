package com.youcode.SmartShop.service;
import com.youcode.SmartShop.dtos.response.OrderItemResponseDto;
import com.youcode.SmartShop.entity.OrderItem;
import com.youcode.SmartShop.entity.Product;
import com.youcode.SmartShop.exception.NotFoundException;
import com.youcode.SmartShop.exception.ProductStockUnavailableException;
import com.youcode.SmartShop.mapper.OrderItemMapper;
import com.youcode.SmartShop.repository.OrderItemRepository;
import com.youcode.SmartShop.repository.ProductRepository;
import com.youcode.SmartShop.service.impl.OrderItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceImplTest {

    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private OrderItemMapper orderItemMapper;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    private Product product;
    private OrderItem orderItem;

    @BeforeEach
    void setup() {
        product = new Product(1L, "nom  prduit", BigDecimal.valueOf(100), 10);

        orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setQuantite(5);
        orderItem.setProduct(product);
    }

    @Test
    void saveTest() {
        when(productRepository.existsById(product.getId())).thenReturn(true);
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(orderItemRepository.save(orderItem)).thenReturn(orderItem);
        when(orderItemMapper.toDTO(orderItem)).thenReturn(new OrderItemResponseDto(1L, 5, BigDecimal.valueOf(100), BigDecimal.valueOf(500), product));

        OrderItemResponseDto result = orderItemService.save(orderItem);

        assertNotNull(result);
        assertEquals(5, result.quantite());
        verify(orderItemRepository).save(orderItem);
    }

    @Test
    void saveThrowNotFound() {
        when(productRepository.existsById(product.getId())).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> orderItemService.save(orderItem));

        assertTrue(exception.getMessage().contains("produit introuvable"));
    }

    @Test
    void saveThrowProductStockUnavailable() {
        product.setStock(3);
        when(productRepository.existsById(product.getId())).thenReturn(true);
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(orderItemRepository.save(orderItem)).thenReturn(orderItem);

        ProductStockUnavailableException exception = assertThrows(ProductStockUnavailableException.class,
                () -> orderItemService.save(orderItem));

        assertTrue(exception.getMessage().contains("La quantite demandee depasse le stock disponible"));
    }
}
