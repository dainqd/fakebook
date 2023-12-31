package com.example.fakebook.config;

import com.example.fakebook.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] IGNORE_PATHS = {
            // env
            "/", "/css/**", "/img/**", "/favicon.ico",
            //config swagger
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/csrf",
            "/privacy",
            "/v2/api-docs",
            "/configuration/ui",
            // templates
            "/js/**",
            "/fonts/**",
            "/lib/**",
            "/icons/**",
            "/assets/**",
            "/auth/**",
            "/main/**",
            "/error/**",
            "/v1/**",
            "/views/**",
            "/layout/**",
            "/templates/**",
            "/images/**",
            // auth api
            "/api/v1/auth/**",
            // auth url
            "/login",
            "/register",
            // websocket
            "/websocket/**",
            "/ws/**",
            "/topic/**",
            "/app/**",
            //main url
            "/api/v1/comments/list/**",
            "/api/v1/marketing/**",
            "/message/**",
            "/blog/**",
            "/photos/**",
            "/friends/**",
            "/groups/**",
            "/notifications/**"
    };

    private static final String[] USER_PATHS = {
            "/api/v1/user/**",
            "/api/v1/comments/**",
            "/api/v1/blogs/**",
            "/api/v1/friendships/**",
    };

    private static final String[] ADMIN_PATHS = {
            "/admin/**",
    };

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public com.example.fakebook.config.AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/v1/auth/**").permitAll()
                .antMatchers(IGNORE_PATHS).permitAll()
                .antMatchers(USER_PATHS).hasAuthority("USER")
                .antMatchers(ADMIN_PATHS).hasAuthority("ADMIN")
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}