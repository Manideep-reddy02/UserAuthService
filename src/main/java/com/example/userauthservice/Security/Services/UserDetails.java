package com.example.userauthservice.Security.Services;


import com.example.userauthservice.models.Role;
import com.example.userauthservice.models.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private String userName;
    private String password;
    private List<GrantedAuthority> grantedAuthorityList;

    public UserDetails(User user) {
        this.userName = user.getEmail();
        this.password = user.getPassword();
         grantedAuthorityList = new ArrayList<>();

         for(Role role:user.getRoles()){
             grantedAuthorityList.add(new com.example.userauthservice.Security.Services.GrantedAuthority(role));
         }
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return  grantedAuthorityList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
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
