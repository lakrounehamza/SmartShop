package com.youcode.SmartShop.config;

import com.youcode.SmartShop.enums.UserRole;
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

        UserRole role = (UserRole) session.getAttribute("role");

        if (role != UserRole.ADMIN) {
            response.setStatus(403);
            response.getWriter().write("Accès interdit : ADMIN requis");
            return false;
        }

        return true;
    }
}
