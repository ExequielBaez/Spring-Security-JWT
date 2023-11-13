package com.jwt.springsecurity.persistence.entity;

import com.jwt.springsecurity.persistence.util.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Data
public class UserEntity implements UserDetails {//para usar en securitybeans el dao necesito un
                                //userdetails por eso implemnto la interfaz
                                //respresenta al usuario logueado en sprigboot tiene sus propios metodos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    private String userName;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(role == null) return null;

        if(role.getPermissions() == null) return null;

        return role.getPermissions().stream()
                .map(each -> each.name())
                .map(each -> new SimpleGrantedAuthority(each))
                        //.map(each -> {
                        //String permission = each.name();
                        //return new SimpleGrantedAuthority(permission);
                   // })
                    .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
