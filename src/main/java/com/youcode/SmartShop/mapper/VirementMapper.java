package com.youcode.SmartShop.mapper;

import com.youcode.SmartShop.dtos.request.VirementCreateRequestDto;
import com.youcode.SmartShop.dtos.response.VirementResponseDto;
import com.youcode.SmartShop.entity.Commande;
import com.youcode.SmartShop.entity.Virement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VirementMapper {

    @Mapping(target = "commande", expression = "java(mapCommande(request.commande_id()))")
    Virement  toEntity(VirementCreateRequestDto  request);
//    @Mapping(target = "commande_id", source = "commande.id")
    VirementResponseDto toDTO(Virement  entity);
    default Commande mapCommande(long id) {
        Commande c = new Commande();
        c.setId(id);
        return c;
    }
}
