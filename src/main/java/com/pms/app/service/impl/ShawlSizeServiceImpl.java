package com.pms.app.service.impl;

import com.pms.app.domain.ShawlSize;
import com.pms.app.repo.ShawlSizeRepository;
import com.pms.app.schema.ShawlSizeDto;
import com.pms.app.service.ShawlSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */

@Service
public class ShawlSizeServiceImpl implements ShawlSizeService {
    private final ShawlSizeRepository shawlSizeRepository;

    @Autowired
    public ShawlSizeServiceImpl(ShawlSizeRepository shawlSizeRepository) {
        this.shawlSizeRepository = shawlSizeRepository;
    }

    @Override
    public List<ShawlSize> getAll() {
        return (List<ShawlSize>) shawlSizeRepository.findAll();
    }

    @Override
    public ShawlSize add(ShawlSizeDto shawlSizeDto) {
        if (shawlSizeDto.getName() == null) {
            throw new RuntimeException("SHAWL Size name not available");
        }
        ShawlSize existingShawlSize = shawlSizeRepository.findByName(shawlSizeDto.getName());
        if (existingShawlSize != null) {
            throw new RuntimeException("SHAWL Size with name " + shawlSizeDto.getName() + " already exist");
        }
        ShawlSize shawlSize = new ShawlSize();
        shawlSize.setName(shawlSizeDto.getName());
        return shawlSizeRepository.save(shawlSize);
    }

    @Override
    public ShawlSize get(Long id) {
        ShawlSize shawlSize = shawlSizeRepository.findOne(id);
        if (shawlSize == null) {
            throw new RuntimeException("No SHAWL Size found ");
        }
        return shawlSize;
    }

    @Override
    public ShawlSize edit(Long id, ShawlSizeDto shawlSizeDto) {
        if (shawlSizeDto.getName() == null) {
            throw new RuntimeException("SHAWL Size name not available");
        }
        ShawlSize existingShawlSize = shawlSizeRepository.findByName(shawlSizeDto.getName());
        if (existingShawlSize != null) {
            throw new RuntimeException("SHAWL Size with same name " + shawlSizeDto.getName() + " already exist");
        }
        ShawlSize shawlSize = shawlSizeRepository.findOne(id);
        shawlSize.setName(shawlSizeDto.getName());
        return shawlSizeRepository.save(shawlSize);
    }
}
