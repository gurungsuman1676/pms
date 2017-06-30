package com.pms.app.service.impl;

import com.pms.app.domain.Shawl;
import com.pms.app.repo.ShawlRepository;
import com.pms.app.schema.ShawlDto;
import com.pms.app.service.ShawlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */

@Service
public class ShawlServiceImpl implements ShawlService {
    private final ShawlRepository shawlRepository;

    @Autowired
    public ShawlServiceImpl(ShawlRepository shawlRepository) {
        this.shawlRepository = shawlRepository;
    }

    @Override
    public List<Shawl> getAll() {
        return (List<Shawl>) shawlRepository.findAll();
    }

    @Override
    public Shawl add(ShawlDto shawlDto) {
        if (shawlDto.getName() == null) {
            throw new RuntimeException("SHAWL name not available");
        }
        Shawl existingShawl = shawlRepository.findByName(shawlDto.getName());
        if (existingShawl != null) {
            throw new RuntimeException("SHAWL with name " + shawlDto.getName() + " already exist");
        }
        Shawl shawl = new Shawl();
        shawl.setName(shawlDto.getName());
        return shawlRepository.save(shawl);
    }

    @Override
    public Shawl get(Long id) {
        Shawl shawl = shawlRepository.findOne(id);
        if (shawl == null) {
            throw new RuntimeException("No shawl found ");
        }
        return shawl;
    }

    @Override
    public Shawl edit(Long id, ShawlDto shawlDto) {
        if (shawlDto.getName() == null) {
            throw new RuntimeException("SHAWL name not available");
        }
        Shawl existingShawl = shawlRepository.findByName(shawlDto.getName());
        if (existingShawl != null) {
            throw new RuntimeException("SHAWL with same name " + shawlDto.getName() + " already exist");
        }
        Shawl shawl = shawlRepository.findOne(id);
        shawl.setName(shawlDto.getName());
        return shawlRepository.save(shawl);
    }
}
