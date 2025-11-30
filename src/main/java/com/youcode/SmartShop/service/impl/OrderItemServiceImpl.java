package com.youcode.SmartShop.service.impl;

import com.youcode.SmartShop.dtos.request.OrderItemCreateRequestDto;
import com.youcode.SmartShop.dtos.response.OrderItemResponseDto;
import com.youcode.SmartShop.entity.OrderItem;
import com.youcode.SmartShop.exception.NotFoundException;
import com.youcode.SmartShop.exception.ProductStockUnavailableException;
import com.youcode.SmartShop.mapper.OrderItemMapper;
import com.youcode.SmartShop.repository.OrderItemRepository;
import com.youcode.SmartShop.repository.ProductRepository;
import com.youcode.SmartShop.service.interfaces.IOrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderItemServiceImpl implements IOrderItemService {
    private final OrderItemRepository repository;
    private final OrderItemMapper mapper;
    private final ProductRepository productRepository;
    @Override
    public OrderItemResponseDto save(OrderItem requets) {
        //OrderItem orderItem = mapper.toEntity(requets);
        if(!productRepository.existsById(requets.getProduct().getId()) ||requets.getProduct()==null )
            throw new NotFoundException("produit introuvable avec l'id "+requets.getProduct().getId());
        OrderItem orderItemSaved = repository.save(requets);
        if(productRepository.findById(requets.getProduct().getId()).get().getStock()<requets.getQuantite())
            throw new ProductStockUnavailableException("La quantite demandee depasse le stock disponible pour le produit " + requets.getProduct().getId());
        return mapper.toDTO(orderItemSaved);
    }

    @Override
    public OrderItemResponseDto getById(Long id) {
        return  mapper.toDTO(repository.findById(id).orElseThrow(()->new NotFoundException("orderitem introuvable avec l'id"+id)));
    }

    @Override
    public Page<OrderItemResponseDto> getByCommadeId(Long id , Pageable pageable) {
         Page<OrderItem>  page  = repository.findByCommandeId(id,pageable);
         if(page.getTotalElements()<1)
             throw  new NotFoundException("aucun orderItem trouve");
         return page.map(mapper::toDTO);
    }
}
