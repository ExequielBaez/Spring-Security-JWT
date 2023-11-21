package com.jwt.springsecurity.config.security.filter;

import com.jwt.springsecurity.exception.ObjectNotFoundException;
import com.jwt.springsecurity.persistence.entity.UserEntity;
import com.jwt.springsecurity.service.UserService;
import com.jwt.springsecurity.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("ENTRO EN EL FILTRO JWT AUTH");

        //1 Obtener encabezado http llamado authorization
        String authorizationHeader = request.getHeader("Authorization");
        if(!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")){//ojo Bearer y espacio
            filterChain.doFilter(request, response);
            return; //en void el return devuelve el control al que llamo al metodo, o sea el do
        }

        //2 Obtener el token jwt desde el encabezado
        String jwt = authorizationHeader.split(" ")[1]; //el split divide en el espacio la pos 0 tiene el bearer
                                                           //la pos 1 tiene el jwt

        //3 Obtener el subject/username desde el token
        //esta accion a su vez valida el formato del token, firma y fecha de expiration
        String userName = jwtService.extractUserName(jwt);

        //4 Setear objeto authentication dentro de security context holder
        UserEntity userEntity = userService.findOneByUserName(userName)
                .orElseThrow(() -> new ObjectNotFoundException("User not found. UserName: " + userName));

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userName, null, userEntity.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        //5 Ejecutar el registro de filtros
        filterChain.doFilter(request, response);
    }
}
