package com.pms.app.startup;


import com.pms.app.domain.*;
import com.pms.app.repo.CurrencyRepository;
import com.pms.app.repo.LocationRepository;
import com.pms.app.repo.PrintRepository;
import com.pms.app.repo.SizeRepository;
import com.pms.app.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AdminUserCreator {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LocationRepository locationRepository;
    private final PrintRepository printRepository;
    private final SizeRepository sizeRepository;
    private final CurrencyRepository currencyRepository;

    @Autowired
    public AdminUserCreator(UserRepository userRepository, PasswordEncoder passwordEncoder, LocationRepository locationRepository, PrintRepository printRepository, SizeRepository sizeRepository, CurrencyRepository currencyRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.locationRepository = locationRepository;
        this.printRepository = printRepository;
        this.sizeRepository = sizeRepository;
        this.currencyRepository = currencyRepository;
    }

    @PostConstruct
    public void init() {

        if (locationRepository.count() == 0) {
            createLocations();
        }
        if (userRepository.count() == 0) {
            createAdmin();
        }
        if (printRepository.getDefaultPrintLessPrint() == null) {
            addDefaultPrintLessPrint();
        }
    }

    private void addDefaultPrintLessPrint() {
        Sizes sizes = new Sizes();
        sizes.setName("Default");
        sizes = sizeRepository.save(sizes);
        Currency currency = new Currency();
        currency.setName("?");
        currency = currencyRepository.save(currency);
        Prints prints = new Prints();
        prints.setName("Printless");
        prints.setSize(sizes);
        prints.setAmount(0D);
        prints.setCurrency(currency);
        printRepository.save(prints);
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