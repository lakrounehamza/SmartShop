package com.youcode.SmartShop.mapper;

import com.youcode.SmartShop.dtos.request.UserCreateRequestDto;
import com.youcode.SmartShop.dtos.response.UserResponseDto;
import com.youcode.SmartShop.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User  toEntity(UserCreateRequestDto request);
    UserResponseDto  toDTO(User  entity);
}
