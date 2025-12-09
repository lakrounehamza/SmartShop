package com.youcode.SmartShop.service.interfaces;

import com.youcode.SmartShop.dtos.request.LoginRequestDto;
import com.youcode.SmartShop.dtos.response.LoginResponseDto;

public interface IAuthService {
    LoginResponseDto login(LoginRequestDto request);
    void logout();
    Object profile();
}
