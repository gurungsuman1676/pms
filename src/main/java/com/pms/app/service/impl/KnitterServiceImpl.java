package com.pms.app.service.impl;

import com.pms.app.domain.Knitter;
import com.pms.app.repo.KnitterRepository;
import com.pms.app.schema.KnitterDto;
import com.pms.app.service.KnitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by arjun on 6/21/2017.
 */

@Service
public class KnitterServiceImpl implements KnitterService {
    private final KnitterRepository knitterRepository;

    @Autowired
    public KnitterServiceImpl(KnitterRepository knitterRepository) {
        this.knitterRepository = knitterRepository;
    }

    @Override
    @Cacheable(cacheNames = "knitters")
    public List<Knitter> getAll() {
        return (List<Knitter>) knitterRepository.findAll();
    }

    @Override
    @CacheEvict(cacheNames = "knitters",allEntries = true)
    public Knitter add(KnitterDto knitterDto) {
        if (knitterDto.getName() == null) {
            throw new RuntimeException("Knitter name not available");
        }
        Knitter existingKnitter = knitterRepository.findByName(knitterDto.getName());
        if (existingKnitter != null) {
            throw new RuntimeException("Knitter with name " + knitterDto.getName() + " already exist");
        }
        Knitter knitter = new Knitter();
        knitter.setName(knitterDto.getName());
        return knitterRepository.save(knitter);
    }

    @Override
    public Knitter get(Long id) {
        Knitter knitter = knitterRepository.findOne(id);
        if (knitter == null) {
            throw new RuntimeException("No knitters found ");
        }
        return knitter;
    }

    @Override
    @CacheEvict(cacheNames = "knitters",allEntries = true)
    public Knitter edit(Long id, KnitterDto knitterDto) {
        if (knitterDto.getName() == null) {
            throw new RuntimeException("Knitter name not available");
        }
        Knitter existingKnitter = knitterRepository.findByName(knitterDto.getName());
        if (existingKnitter != null) {
            throw new RuntimeException("Knitter with same name " + knitterDto.getName() + " already exist");
        }
        Knitter knitter = knitterRepository.findOne(id);
        knitter.setName(knitterDto.getName());
        return knitterRepository.save(knitter);
    }
}
