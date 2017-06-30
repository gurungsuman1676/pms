package com.pms.app.service.impl;

import com.pms.app.domain.ShawlCustomer;
import com.pms.app.repo.ShawlCustomerRepository;
import com.pms.app.schema.ShawlCustomerDto;
import com.pms.app.service.ShawlCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */

@Component
public class ShawlCustomerServiceImpl implements ShawlCustomerService {
    private final ShawlCustomerRepository shawlCustomerRepository;

    @Autowired
    public ShawlCustomerServiceImpl(ShawlCustomerRepository shawlCustomerRepository) {
        this.shawlCustomerRepository = shawlCustomerRepository;
    }

    @Override
    public List<ShawlCustomer> getAll() {
        return (List<ShawlCustomer>) shawlCustomerRepository.findAll();
    }

    @Override
    public ShawlCustomer add(ShawlCustomerDto shawlCustomerDto) {
        if (shawlCustomerDto.getName() == null) {
            throw new RuntimeException("SHAWL Customer name not available");
        }
        ShawlCustomer existingShawlCustomer = shawlCustomerRepository.findByName(shawlCustomerDto.getName());
        if (existingShawlCustomer != null) {
            throw new RuntimeException("SHAWL Customer with name " + shawlCustomerDto.getName() + " already exist");
        }
        ShawlCustomer shawlCustomer = new ShawlCustomer();
        shawlCustomer.setName(shawlCustomerDto.getName());
        return shawlCustomerRepository.save(shawlCustomer);
    }

    @Override
    public ShawlCustomer get(Long id) {
        ShawlCustomer shawlCustomer = shawlCustomerRepository.findOne(id);
        if (shawlCustomer == null) {
            throw new RuntimeException("No SHAWL Customer found ");
        }
        return shawlCustomer;
    }

    @Override
    public ShawlCustomer edit(Long id, ShawlCustomerDto shawlCustomerDto) {
        if (shawlCustomerDto.getName() == null) {
            throw new RuntimeException("SHAWL Customer name not available");
        }
        ShawlCustomer existingShawlCustomer = shawlCustomerRepository.findByName(shawlCustomerDto.getName());
        if (existingShawlCustomer != null) {
            throw new RuntimeException("SHAWL Customer with same name " + shawlCustomerDto.getName() + " already exist");
        }
        ShawlCustomer shawlCustomer = shawlCustomerRepository.findOne(id);
        shawlCustomer.setName(shawlCustomerDto.getName());
        return shawlCustomerRepository.save(shawlCustomer);
    }
}
