package com.pms.app.service;

import com.pms.app.domain.Locations;
import com.pms.app.domain.Users;
import com.pms.app.schema.PasswordDto;
import com.pms.app.schema.UserDto;

import java.util.List;

public interface UserService {
    List<Users> getUsers();

    Users addUser(UserDto userDto);

    Users getUser(Long id);

    Users updateUser(Long id, UserDto userDto);

    List<Locations> findUserLocation(Users users);

    void changePassword(Long userId, PasswordDto password);
}
