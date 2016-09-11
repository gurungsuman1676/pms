package com.pms.app.convert.impl;

import com.pms.app.convert.UserConvert;
import com.pms.app.domain.Users;
import com.pms.app.schema.UserResource;
import com.pms.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConvertImpl implements UserConvert {
    private final UserService userService;

    @Autowired
    public UserConvertImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<UserResource> convert(List<Users> users) {
        return users.stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public UserResource convert(Users users) {
        return UserResource.builder()
                .id(users.getId())
                .username(users.getUsername())
                .role(users.getRole())
                .location(userService.findUserLocation(users).stream().collect(Collectors.joining(", ")))
                .build();
    }

}
