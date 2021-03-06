package com.pms.app.service.impl;

import com.pms.app.domain.Yarns;
import com.pms.app.repo.YarnRepository;
import com.pms.app.schema.YarnDto;
import com.pms.app.service.YarnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class YarnServiceImpl implements YarnService {

    private final YarnRepository yarnRepository;

    @Autowired
    public YarnServiceImpl(YarnRepository yarnRepository) {
        this.yarnRepository = yarnRepository;
    }

    @Override
    @Cacheable(cacheNames = "yarns")
    public List<Yarns> getYarns() {
        return  yarnRepository.findAllByOrderByNameAsc();
    }

    @Override
    @CacheEvict(cacheNames = "yarns",allEntries = true)
    public Yarns addYarn(YarnDto yarnDto) {

        Yarns duplicateYarn = yarnRepository.findByName(yarnDto.getName());

        if (duplicateYarn != null) {
            throw new RuntimeException("Yarn already exists");

        }

        Yarns yarns = new Yarns();
        yarns.setName(yarnDto.getName());
        return yarnRepository.save(yarns);
    }

    @Override
    public Yarns getYarn(Long id) {
        Yarns yarns = yarnRepository.findOne(id);
        if (yarns == null) {
            throw new RuntimeException("No Yarn available");

        }
        return yarns;
    }

    @Override
    @CacheEvict(cacheNames = "yarns",allEntries = true)
    public Yarns updateYarn(Long id, YarnDto yarnDto) {
        Yarns yarns = yarnRepository.findOne(id);
        if (yarns == null) {
            throw new RuntimeException("No Yarn available");
        }

        Yarns duplicateYarn = yarnRepository.findByName(yarnDto.getName());

        if (duplicateYarn != null && duplicateYarn.getId() != id) {
            throw new RuntimeException("Yarn already exists");

        }
        yarns.setName(yarnDto.getName());
        return yarnRepository.save(yarns);

    }

  /*  @Override
    public List<Yarns> getYarnByCustomersId(Long customerId) {
        Customers customer = customerRepository.findOne(customerId);
        if(customer.getParent() != null){
            customerId = customer.getParent().getId();
        }
        return yarnRepository.findByCustomerId(customerId);
    }*/
}
