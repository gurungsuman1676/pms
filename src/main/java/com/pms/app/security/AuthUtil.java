package com.pms.app.security;

import com.pms.app.domain.Users;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {

    public static Users getCurrentUser() {
        SecurityUserDetails userDetails = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsers();
    }

}
