package com.youcode.SmartShop.mapper;

import com.youcode.SmartShop.dtos.request.ChequeCreateRequestDto;
import com.youcode.SmartShop.dtos.response.ChequeResponseDto;
import com.youcode.SmartShop.entity.Cheque;
import com.youcode.SmartShop.entity.Commande;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChequeMapper {

    @Mapping(target = "commande", expression = "java(mapCommande(requestDto.commande_id()))")
    Cheque toEntity(ChequeCreateRequestDto requestDto);
   // @Mapping(target = "commande_id", source = "commande.id")
    ChequeResponseDto toDTO(Cheque entity);
    default Commande mapCommande(long id) {
        Commande c = new Commande();
        c.setId(id);
        return c;
    }
}

