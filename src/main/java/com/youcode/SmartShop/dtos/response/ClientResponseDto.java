package com.youcode.SmartShop.dtos.response;

import com.youcode.SmartShop.enums.CustomerTier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponseDto {
    private Long id;
    private String nom;
    private String email;
    private CustomerTier niveauFidelite;
    private  Long  user_id;
}
