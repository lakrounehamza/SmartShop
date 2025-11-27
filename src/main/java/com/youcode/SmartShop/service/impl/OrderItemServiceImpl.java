package com.youcode.SmartShop.service.impl;

import com.youcode.SmartShop.dtos.request.OrderItemCreateRequestDto;
import com.youcode.SmartShop.dtos.response.OrderItemResponseDto;
import com.youcode.SmartShop.entity.OrderItem;
import com.youcode.SmartShop.exception.NotFoundException;
import com.youcode.SmartShop.mapper.OrderItemMapper;
import com.youcode.SmartShop.repository.OrderItemRepository;
import com.youcode.SmartShop.service.interfaces.IOrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderItemServiceImpl implements IOrderItemService {
    private final OrderItemRepository repository;
    private final OrderItemMapper mapper;
    @Override
    public OrderItemResponseDto save(OrderItemCreateRequestDto requets) {
        OrderItem orderItem = mapper.toEntity(requets);
        OrderItem orderItemSaved = repository.save(orderItem);
        return mapper.toDTO(orderItemSaved);
    }

    @Override
    public OrderItemResponseDto getById(Long id) {
        return  mapper.toDTO(repository.findById(id).orElseThrow(()->new NotFoundException("orderitem introuvable avec l'id"+id)));
    }

    @Override
    public Page<OrderItemResponseDto> getByCommadeId(Long id) {
         Page<OrderItem>  page  = repository.findByCommandeId(id);
         if(page.getTotalElements()<1)
             throw  new NotFoundException("aucun orderItem trouve");
         return page.map(mapper::toDTO);
    }
}
