package com.pms.app.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class XAuthTokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final UserDetailsService userDetailsService;

    public XAuthTokenConfigurer(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new XAuthTokenFilter(userDetailsService), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Sifts through all incoming requests and installs a Spring Security principal
     * if a header corresponding to a valid user is found.        Session.create(res.id, res.user.id, res.user.role);
     */
    public static class XAuthTokenFilter extends GenericFilterBean {

        private final UserDetailsService userDetailsService;
        private final TokenUtils tokenUtils = new TokenUtils();
        private final String xAuthTokenHeaderName = "x-auth-token";

        public XAuthTokenFilter(UserDetailsService userDetailsService) {
            this.userDetailsService = userDetailsService;
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
            try {

                HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                String authToken = httpServletRequest.getHeader(this.xAuthTokenHeaderName);

                if (StringUtils.hasText(authToken)) {
                    String username = this.tokenUtils.getUserNameFromToken(authToken);

                    UserDetails details = this.userDetailsService.loadUserByUsername(username);

                    if (this.tokenUtils.validateToken(authToken, details)) {
                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details, details.getPassword(), details.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(token);
                    }
                }
                filterChain.doFilter(request, response);
            } catch (Exception ex) {
                log.error("Global Error from Auth Configurer", ex);
                throw new RuntimeException("Oops, Something went wrong.");
            }
        }
    }
}
