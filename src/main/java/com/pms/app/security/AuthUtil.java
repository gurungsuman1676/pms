package com.pms.app.security;

import com.pms.app.domain.Users;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public class AuthUtil {

    public static Users getCurrentUser() throws RuntimeException {
        try {
            SecurityUserDetails userDetails = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userDetails.getUsers();
        }catch (Exception e){
            throw new RuntimeException("Please log in again");
        }
    }

}
