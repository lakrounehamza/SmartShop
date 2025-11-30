package com.youcode.SmartShop.service.interfaces;

import com.youcode.SmartShop.dtos.request.OrderItemCreateRequestDto;
import com.youcode.SmartShop.dtos.response.OrderItemResponseDto;
import com.youcode.SmartShop.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderItemService {
    OrderItemResponseDto save(OrderItem requets);
    OrderItemResponseDto getById(Long id);
    Page<OrderItemResponseDto> getByCommadeId(Long id, Pageable pageable);

}
