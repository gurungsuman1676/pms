package com.pms.app.config;

import com.pms.app.controller.Routes;
import com.pms.app.domain.Locations;
import com.pms.app.domain.Users;
import com.pms.app.repo.UserLocationRepository;
import com.pms.app.repo.UserRepository;
import com.pms.app.security.SecurityUserDetails;
import com.pms.app.security.XAuthTokenConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserRepository userRepository;
    private UserLocationRepository userLocationRepository;

    @Autowired
    public void setSimUserRepository(UserRepository userRepository, UserLocationRepository userLocationRepository) {
        Assert.notNull(userRepository);
        this.userRepository = userRepository;
        this.userLocationRepository = userLocationRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/" + Routes.AUTHENTICATE).permitAll()
                .antMatchers("/api/v1/**").authenticated();

//        @formatter:on
        SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> securityConfigurerAdapter = new XAuthTokenConfigurer(userDetailsServiceBean());
        http.apply(securityConfigurerAdapter);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService(userRepository))
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private UserDetailsService userDetailsService(final UserRepository userRepository) {

        return username -> {
            final Users users = userRepository.findByUsername(username);
            if (users == null) {
                throw new UsernameNotFoundException("User not found.");
            }
            List<Locations> locations = userLocationRepository.findLocationByUser(users);
            return new SecurityUserDetails(users, locations.stream().map(Locations::getName).collect(Collectors.toSet()), locations.size() > 0 ? locations.get(0).getLocationType().name() : null);
        };
    }
}

