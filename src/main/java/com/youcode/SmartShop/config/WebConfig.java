package com.youcode.SmartShop.config;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    private final AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/auth/login");

        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/api/commandes/{id}/status")
                .addPathPatterns("/api/clients")
                .addPathPatterns("/api/codepromo")
                .addPathPatterns("/api/products")
        ;
    }
}
