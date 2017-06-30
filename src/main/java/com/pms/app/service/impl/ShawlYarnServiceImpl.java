package com.pms.app.service.impl;

import com.pms.app.domain.ShawlYarn;
import com.pms.app.repo.ShawlYarnRepository;
import com.pms.app.schema.ShawlYarnDto;
import com.pms.app.service.ShawlYarnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */

@Service
public class ShawlYarnServiceImpl implements ShawlYarnService {
    private final ShawlYarnRepository shawlYarnRepository;

    @Autowired
    public ShawlYarnServiceImpl(ShawlYarnRepository shawlYarnRepository) {
        this.shawlYarnRepository = shawlYarnRepository;
    }

    @Override
    public List<ShawlYarn> getAll() {
        return (List<ShawlYarn>) shawlYarnRepository.findAll();
    }

    @Override
    public ShawlYarn add(ShawlYarnDto shawlYarnDto) {
        if (shawlYarnDto.getName() == null) {
            throw new RuntimeException("SHAWL Yarn name not available");
        }
        ShawlYarn existingShawlYarn = shawlYarnRepository.findByName(shawlYarnDto.getName());
        if (existingShawlYarn != null) {
            throw new RuntimeException("SHAWL Yarn with name " + shawlYarnDto.getName() + " already exist");
        }
        ShawlYarn shawlYarn = new ShawlYarn();
        shawlYarn.setName(shawlYarnDto.getName());
        return shawlYarnRepository.save(shawlYarn);
    }

    @Override
    public ShawlYarn get(Long id) {
        ShawlYarn shawlYarn = shawlYarnRepository.findOne(id);
        if (shawlYarn == null) {
            throw new RuntimeException("No SHAWL Yarn found ");
        }
        return shawlYarn;
    }

    @Override
    public ShawlYarn edit(Long id, ShawlYarnDto shawlYarnDto) {
        if (shawlYarnDto.getName() == null) {
            throw new RuntimeException("SHAWL Yarn name not available");
        }
        ShawlYarn existingShawlYarn = shawlYarnRepository.findByName(shawlYarnDto.getName());
        if (existingShawlYarn != null) {
            throw new RuntimeException("SHAWL Yarn with same name " + shawlYarnDto.getName() + " already exist");
        }
        ShawlYarn shawlYarn = shawlYarnRepository.findOne(id);
        shawlYarn.setName(shawlYarnDto.getName());
        return shawlYarnRepository.save(shawlYarn);
    }
}
