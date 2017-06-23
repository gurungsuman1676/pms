package com.pms.app.startup;


import com.pms.app.domain.*;
import com.pms.app.repo.LocationRepository;
import com.pms.app.repo.UserLocationRepository;
import com.pms.app.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdminUserCreator {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LocationRepository locationRepository;

    @Autowired
    public AdminUserCreator(UserRepository userRepository, PasswordEncoder passwordEncoder, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.locationRepository = locationRepository;
    }
    @PostConstruct
    public void init() {

        if (locationRepository.count() == 0) {
            createLocations();
        }
        if (userRepository.count() == 0) {
            createAdmin();
        }
    }

    private void createLocations() {
        for (LocationEnum location : LocationEnum.values()) {
            Locations locations = new Locations();
            locations.setName(location.getName());
            locationRepository.save(locations);
        }
    }


    private void createAdmin() {
        Users users = new Users();
        users.setPassword(passwordEncoder.encode("test12"));
        users.setUsername("sysadmin");
        users.setRole(Roles.ADMIN);
        userRepository.save(users);
    }


}