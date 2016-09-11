package com.pms.app.controller;


import com.pms.app.convert.UserConvert;
import com.pms.app.schema.PasswordDto;
import com.pms.app.schema.UserDto;
import com.pms.app.schema.UserResource;
import com.pms.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = Routes.USER)
public class UserController {

    private final UserService userService;
    private final UserConvert userConvert;
    private static final String USER_ID_PASSWORD = "{id}"+"/password";

    @Autowired
    public UserController(UserService userService, UserConvert userConvert) {
        this.userService = userService;
        this.userConvert = userConvert;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<UserResource> getUsers() {
        return userConvert.convert(userService.getUsers());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public UserResource addUser(@RequestBody UserDto userDto) {
        return userConvert.convert(userService.addUser(userDto));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserResource getUser(@PathVariable Long id) {
        return userConvert.convert(userService.getUser(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public UserResource updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        return userConvert.convert(userService.updateUser(id, userDto));
    }

    @RequestMapping(value = USER_ID_PASSWORD,method = RequestMethod.POST)
    public void changePassword(@RequestBody PasswordDto password,@PathVariable Long userId ){
        userService.changePassword(userId,password);
    }
}
