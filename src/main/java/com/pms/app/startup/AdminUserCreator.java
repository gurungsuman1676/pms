package com.pms.app.startup;


import com.pms.app.domain.*;
import com.pms.app.repo.LocationRepository;
import com.pms.app.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

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
            createKnittingLocations();
            createShawlLocation();
        } else {
            List<Locations> currentLocations = locationRepository.findAllByLocationTypeOrderByNameAsc(LocationType.KNITTING);
            if (currentLocations == null || currentLocations.isEmpty()) {
                List<Locations> locations = (List<Locations>) locationRepository.findAll();
                for (Locations location : locations) {
                    location.setLocationType(LocationType.KNITTING);
                    locationRepository.save(location);
                }
                createShawlLocation();
            }

        }

        if (userRepository.count() == 0) {
            createAdmin();
        }
    }

    private void createKnittingLocations() {
        for (LocationEnum location : LocationEnum.values()) {
            switch (location) {
                case SHIPPING:
                case PRE_KNITTING:
                case PRE_KNITTING_COMPLETED: {
                    Locations locations = new Locations();
                    locations.setName(location.getName());
                    locations.setLocationType(LocationType.KNITTING);
                    locationRepository.save(locations);
                }
            }
        }
    }

    public void createShawlLocation() {
        for (LocationEnum location : LocationEnum.values()) {
            switch (location) {
                case REJECTED: {
                    Locations locations = new Locations();
                    locations.setName(location.getName());
                    locations.setLocationType(LocationType.SHAWL);
                    locationRepository.save(locations);
                    break;
                }
            }
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