package com.youcode.SmartShop.service.impl;

import com.youcode.SmartShop.dtos.request.LoginRequestDto;
import com.youcode.SmartShop.dtos.response.LoginResponseDto;
import com.youcode.SmartShop.entity.User;
import com.youcode.SmartShop.exception.IncorrectPasswordException;
import com.youcode.SmartShop.exception.NotFoundException;
import com.youcode.SmartShop.repository.UserRepository;
import com.youcode.SmartShop.service.interfaces.IAuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final HttpSession session;
    private final UserRepository userRepository;

    @Override
    public LoginResponseDto login(LoginRequestDto request) {

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new NotFoundException("username incorrect."));
        if (user.getRole()==null)
            throw new NotFoundException(" username  est suprimer");
        if (!BCrypt.checkpw(request.password(), user.getPassword())) {
            throw new IncorrectPasswordException("password incorrect.");
        }

        session.setAttribute("id", user.getId());
        session.setAttribute("role", user.getRole());

        return new LoginResponseDto(
                "Connexion reussie",
                user.getId(),
                user.getUsername(),
                user.getRole().name()
        );
    }

    @Override
    public void logout() {
        session.invalidate();
    }
}
