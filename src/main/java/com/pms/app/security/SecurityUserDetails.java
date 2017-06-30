package com.pms.app.security;

import com.pms.app.domain.Users;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SecurityUserDetails implements UserDetails {

    @Getter
    private final Users users;

    @Getter
    private final Set<String> locations;

    @Getter
    private final String type;

    public SecurityUserDetails(Users users, Set<String> locations, String type) {
        this.users = users;
        this.locations = locations;
        this.type = type;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = locations.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority(users.getRole().toString()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getUsername();
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
