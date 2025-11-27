package com.youcode.SmartShop.service.interfaces;

import com.youcode.SmartShop.dtos.request.OrderItemCreateRequestDto;
import com.youcode.SmartShop.dtos.response.OrderItemResponseDto;
import org.springframework.data.domain.Page;

public interface IOrderItemService {
    OrderItemResponseDto save(OrderItemCreateRequestDto requets);
    OrderItemResponseDto getById(Long id);
    Page<OrderItemResponseDto> getByCommadeId(Long id);

}
