package com.youcode.SmartShop.mapper;

import com.youcode.SmartShop.dtos.request.EspecesCreateRequestDto;
import com.youcode.SmartShop.dtos.response.EspecesResponseDto;
import com.youcode.SmartShop.entity.Commande;
import com.youcode.SmartShop.entity.Especes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EspecesMapper {

    @Mapping(target = "commande", expression = "java(mapCommande(request.commande_id()))")
    Especes  toEntity(EspecesCreateRequestDto request);
//    @Mapping(target = "commande_id", source = "commande.id")
    EspecesResponseDto toDTO(Especes entity);
    default Commande mapCommande(long id) {
        Commande c = new Commande();
        c.setId(id);
        return c;
    }
}
