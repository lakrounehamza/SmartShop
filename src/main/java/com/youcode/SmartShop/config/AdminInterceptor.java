package com.youcode.SmartShop.config;

import com.youcode.SmartShop.enums.UserRole;
import com.youcode.SmartShop.exception.AuthorizationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        String path = request.getRequestURI();
        String method = request.getMethod();

        UserRole role = (UserRole) session.getAttribute("role");
        boolean requiresAdmin =
                (path.startsWith("/api/commande/{id}/status") && "PATCH".equals(method)) ||
                        (path.startsWith("/api/clients") && "POST".equals(method)) ||
                        (path.startsWith("/api/products") && "POST".equals(method)) ||
                        (path.startsWith("/api/products/{id}") && "PATCH".equals(method)) ||
                        (path.startsWith("/api/clients") && "GET".equals(method)) ||
                        (path.startsWith("/api/commandes") && "GET".equals(method)) ||
                        (path.startsWith("/api/client") && "DELETE".equals(method));

        if (requiresAdmin && role != UserRole.ADMIN) {
            throw  new AuthorizationException("Acces interdit : ADMIN requis");
        }

        return true;
    }
}
