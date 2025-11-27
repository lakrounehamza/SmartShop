package com.youcode.SmartShop.dtos.response;

import com.youcode.SmartShop.enums.UserRole;
import lombok.Builder;

@Builder
public record UserResponseDto (
     long id,
     String username,
     String password,
     UserRole role){}
