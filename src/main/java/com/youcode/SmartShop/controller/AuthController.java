package com.youcode.SmartShop.controller;

import com.youcode.SmartShop.dtos.request.LoginRequestDto;
import com.youcode.SmartShop.dtos.response.LoginResponseDto;
import com.youcode.SmartShop.service.interfaces.IAuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto request) {
        return authService.login(request);
    }

    @PostMapping("/logout")
    public String logout() {
        authService.logout();
        return "Deconnexion reussie";
    }
}
