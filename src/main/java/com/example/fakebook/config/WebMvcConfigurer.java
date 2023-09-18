package com.example.fakebook.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
@RequiredArgsConstructor
class WebMvcConfig implements WebMvcConfigurer {
    private static final String[] ALLOWED_METHODS = {"GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"};
    private static final String ALLOWED_HEADER = "*";
    private static final String ADD_MAPPING = "/**";
    final long MAX_AGE_SECS = 60 * 60;
    final LocaleChangeInterceptor localeChangeInterceptor;

    // To change language. Example: ?lang=vi
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor);
    }

    @Value("${app.cors.allowed-origins}")
    private String[] allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(ADD_MAPPING)
                .allowedOriginPatterns(allowedOrigins)
                .allowedMethods(ALLOWED_METHODS)
                .allowedHeaders(ALLOWED_HEADER)
                .allowCredentials(false)
                .maxAge(MAX_AGE_SECS);
    }
}

