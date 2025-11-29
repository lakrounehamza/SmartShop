package com.youcode.SmartShop.mapper;

import com.youcode.SmartShop.dtos.request.CommandeCreateRequestDto;
import com.youcode.SmartShop.dtos.response.CommandeResponseDto;
import com.youcode.SmartShop.entity.Client;
import com.youcode.SmartShop.entity.Commande;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommandeMapper {
    @Mapping(target = "client", expression = "java(mapClient(request.client_id()))")
    Commande toEntity(CommandeCreateRequestDto request);
    @Mapping(target = "client_id", source = "client.id")
    CommandeResponseDto toDTO(Commande entity);
    default Client mapClient(long id) {
        Client c = new Client();
        c.setId(id);
        return c;
    }
}
