package com.pms.app.service.impl;

import com.pms.app.domain.Locations;
import com.pms.app.domain.Roles;
import com.pms.app.domain.UserLocations;
import com.pms.app.domain.Users;
import com.pms.app.repo.LocationRepository;
import com.pms.app.repo.UserLocationRepository;
import com.pms.app.repo.UserRepository;
import com.pms.app.schema.PasswordDto;
import com.pms.app.schema.UserDto;
import com.pms.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserLocationRepository userLocationRepository;
    private final PasswordEncoder passwordEncoder;
    private final LocationRepository locationRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserLocationRepository userLocationRepository, PasswordEncoder passwordEncoder, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.userLocationRepository = userLocationRepository;
        this.passwordEncoder = passwordEncoder;
        this.locationRepository = locationRepository;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<Users> getUsers() {
        return (List<Users>) userRepository.findAll();
    }

    @Override
    public Users addUser(UserDto userDto) {
        Users duplicateUser = userRepository.findByUsername(userDto.getUsername());
        if (duplicateUser != null) {
            throw new RuntimeException("User already exists");
        }
        Users users = new Users();
        users.setPassword(passwordEncoder.encode(userDto.getPassword()));
        users.setUsername(userDto.getUsername());
        users.setRole(userDto.getRole());
        users = userRepository.save(users);

        if (userDto.getRole() != Roles.ADMIN) {
            if (userDto.getLocation() == null) {
                throw new RuntimeException("User must have location");
            }
            Locations locations = locationRepository.findOne(userDto.getLocation());

            UserLocations userLocations = getLocations(locations, users);
            userLocationRepository.save(userLocations);
        }
        return users;
    }

    private UserLocations getLocations(Locations locations, Users users) {
        UserLocations userLocations = new UserLocations();
        userLocations.setLocation(locations);
        userLocations.setUser(users);
        return userLocations;
    }

    @Override
    public Users getUser(Long id) {
        Users users = userRepository.findOne(id);
        if (users == null) {
            throw new RuntimeException("No such user found");
        }
        return users;
    }

    @Override
    public Users updateUser(Long id, UserDto userDto) {
        Users user = getUser(id);
        if (userDto.getRole() != Roles.ADMIN) {
            UserLocations userLocations = userLocationRepository.findUserLocationByUser(user).get(0);
            userLocations.setLocation(locationRepository.findOne(userDto.getLocation()));
        }
        return userRepository.save(user);
    }

    @Override
    public List<Locations> findUserLocation(Users users) {
        return userLocationRepository.findLocationByUser(users);
    }

    @Override
    public void changePassword(Long userId, PasswordDto password) {
        Users users = userRepository.findOne(userId);
        if (users == null) {
            throw new RuntimeException("No such user found");
        }
        users.setPassword(passwordEncoder.encode(password.getPassword()));
        userRepository.save(users);
    }
}
