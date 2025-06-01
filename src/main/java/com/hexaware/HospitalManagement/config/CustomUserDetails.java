package com.hexaware.HospitalManagement.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hexaware.HospitalManagement.entity.User;
import com.hexaware.HospitalManagement.entity.User.Role;
public class CustomUserDetails implements UserDetails {

    private Long id;          
    private String email;
    private String password;
    private SimpleGrantedAuthority authority;

    public CustomUserDetails(User user) {
        this.id = user.getUserId(); 
        this.email = user.getEmail(); 
        this.password = user.getPassword();

        Role role = user.getRole();
        if (role != null) {
            this.authority = new SimpleGrantedAuthority("ROLE_" + role.name());
        } else {
            throw new IllegalArgumentException("User must have a valid role.");
        }
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email; 
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
