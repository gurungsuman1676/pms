package com.pms.app.service.impl;

import com.pms.app.domain.ShawlColor;
import com.pms.app.repo.ShawlColorRepository;
import com.pms.app.schema.ShawlColorDto;
import com.pms.app.service.ShawlColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */

@Service
public class ShawlColorServiceImpl implements ShawlColorService {
    private final ShawlColorRepository shawlColorRepository;

    @Autowired
    public ShawlColorServiceImpl(ShawlColorRepository shawlColorRepository) {
        this.shawlColorRepository = shawlColorRepository;
    }

    @Override
    public List<ShawlColor> getAll() {
        return (List<ShawlColor>) shawlColorRepository.findAll();
    }

    @Override
    public ShawlColor add(ShawlColorDto shawlColorDto) {
        if (shawlColorDto.getName() == null) {
            throw new RuntimeException("SHAWL Color name not available");
        }
        ShawlColor existingShawlColor = shawlColorRepository.findByName(shawlColorDto.getName());
        if (existingShawlColor != null) {
            throw new RuntimeException("SHAWL Color with name " + shawlColorDto.getName() + " already exist");
        }
        ShawlColor shawlColor = new ShawlColor();
        shawlColor.setName(shawlColorDto.getName());
        return shawlColorRepository.save(shawlColor);
    }

    @Override
    public ShawlColor get(Long id) {
        ShawlColor shawlColor = shawlColorRepository.findOne(id);
        if (shawlColor == null) {
            throw new RuntimeException("No SHAWL Color found ");
        }
        return shawlColor;
    }

    @Override
    public ShawlColor edit(Long id, ShawlColorDto shawlColorDto) {
        if (shawlColorDto.getName() == null) {
            throw new RuntimeException("SHAWL Color name not available");
        }
        ShawlColor existingShawlColor = shawlColorRepository.findByName(shawlColorDto.getName());
        if (existingShawlColor != null) {
            throw new RuntimeException("SHAWL Color with same name " + shawlColorDto.getName() + " already exist");
        }
        ShawlColor shawlColor = shawlColorRepository.findOne(id);
        shawlColor.setName(shawlColorDto.getName());
        return shawlColorRepository.save(shawlColor);
    }
}
