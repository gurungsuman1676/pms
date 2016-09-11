package com.pms.app.security;

import com.pms.app.controller.Routes;
import com.pms.app.schema.Login;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
public class AuthenticationController {

    private final TokenUtils tokenUtils = new TokenUtils();
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value = Routes.AUTHENTICATE, method = POST)
    public UserTransfer authorize(@RequestBody @Valid Login login) {

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails details = this.userDetailsService.loadUserByUsername(login.getUsername());

        Set<String> roles = new HashSet<>();
        for (GrantedAuthority authority : details.getAuthorities()) {
            roles.add(authority.toString());
        }


        return new UserTransfer(details.getUsername(), roles, tokenUtils.createToken(details));
    }

    @Getter
    @Setter
    static class UserTransfer {

        private final String username;
        private final Set<String> roles;
        private final String token;

        public UserTransfer(String username, Set<String> roles, String token) {
            this.username = username;
            this.roles = roles;
            this.token = token;
        }
    }
}

