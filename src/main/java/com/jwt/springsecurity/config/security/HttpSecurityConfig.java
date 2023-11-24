package com.jwt.springsecurity.config.security;

import com.jwt.springsecurity.config.security.filter.JwtAuthenticationFilter;
import com.jwt.springsecurity.persistence.util.Role;
import com.jwt.springsecurity.persistence.util.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class HttpSecurityConfig {

    @Autowired
    private AuthenticationProvider daoAuthprovider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        SecurityFilterChain filterChain = http
                .csrf(csrfConfig -> csrfConfig.disable())//que no use este tipo de token
                .sessionManagement(sessMagConfig -> sessMagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(daoAuthprovider)//strategia para a hacer login
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests( authReqConfig -> {

                    buildRequestMatchersV2(authReqConfig);

                })
                .build();

        return filterChain;
    }

    private static void buildRequestMatchers(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
        //Autorizacion de endpoints de products

        authReqConfig.requestMatchers(HttpMethod.GET, "/products")
                        .hasAnyRole(Role.ADMINISTRATOR.name(), Role.ASSISTANCE_ADMINISTRATOR.name());
        //.hasAuthority(RolePermission.READ_ALL_PRODUCTS.name());

        //authReqConfig.requestMatchers(HttpMethod.GET, "/products/{idProduct}")
        authReqConfig.requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.GET, "/products/[0-9]*"))
                .hasAuthority(RolePermission.READ_ONE_PRODUCT.name());

        authReqConfig.requestMatchers(HttpMethod.POST, "/products")
                .hasAuthority(RolePermission.CREATE_ONE_PRODUCT.name());

        authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{idProduct}/disabled")
                .hasAuthority(RolePermission.DISABLE_ONE_PRODUCT.name());

        //Autorizacion de endpoints de categories

        authReqConfig.requestMatchers(HttpMethod.GET, "/categories")
                .hasAuthority(RolePermission.READ_ALL_CATEGORIES.name());

        authReqConfig.requestMatchers(HttpMethod.GET, "/categories/{idCategory}")
                .hasAuthority(RolePermission.READ_ONE_CATEGORY.name());

        authReqConfig.requestMatchers(HttpMethod.POST, "/categories")
                .hasAuthority(RolePermission.CREATE_ONE_CATEGORY.name());

        authReqConfig.requestMatchers(HttpMethod.PUT, "/categories/{idCategory}/disabled")
                .hasAuthority(RolePermission.DISABLE_ONE_CATEGORY.name());

        ////Autorizacion de endpoint profile

        authReqConfig.requestMatchers(HttpMethod.GET, "/auth/profile")
                .hasAuthority(RolePermission.READ_MY_PROFILE.name());

        ////Autorizacion de endpoints publicos

        authReqConfig.requestMatchers(HttpMethod.POST, "/users").permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET, "/auth/validate").permitAll();


        authReqConfig.anyRequest().authenticated();
    }

    private static void buildRequestMatchersV2(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {

        ////Autorizacion de endpoints publicos

        authReqConfig.requestMatchers(HttpMethod.POST, "/users").permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET, "/auth/validate").permitAll();

        authReqConfig.anyRequest().authenticated();
    }
}
