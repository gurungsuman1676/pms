package com.pms.app.service.impl;

import com.pms.app.domain.Customers;
import com.pms.app.domain.Designs;
import com.pms.app.repo.CustomerRepository;
import com.pms.app.repo.DesignRepository;
import com.pms.app.schema.DesignDto;
import com.pms.app.service.DesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DesignServiceImpl implements DesignService {

    private final DesignRepository designRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public DesignServiceImpl(DesignRepository designRepository, CustomerRepository customerRepository) {
        this.designRepository = designRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Cacheable(cacheNames = "designs")

    public List<Designs> getDesigns() {
        return designRepository.findAllByOrderByNameAsc();
    }

    @Override
    @CacheEvict(cacheNames = "designs",allEntries = true)
    public Designs addDesign(DesignDto designDto) {


        if (designDto.getCustomerId() == null) {
            throw new RuntimeException("Customer is required");
        }
        Customers customers = customerRepository.findOne(designDto.getCustomerId());
        if (customers == null) {
            throw new RuntimeException("No such customer available");
        }


        Designs parent = null;
        Designs duplicateDesign = null;
        if (designDto.getParentId() != null) {
            parent = designRepository.findOne(designDto.getParentId());
            if (parent == null) {
                throw new RuntimeException("No such parent available");
            }
            duplicateDesign = designRepository.findByNameAndParentAndCustomer(designDto.getName(), designDto.getParentId(), designDto.getCustomerId());

        } else {
            duplicateDesign = designRepository.findByNameAndCustomer(designDto.getName(),  designDto.getCustomerId());
        }


        if (duplicateDesign != null) {
            throw new RuntimeException("Design name already exist for customer");
        }
        Designs newDesign = new Designs();
        newDesign.setCustomer(customers);
        newDesign.setName(designDto.getName());
        newDesign.setGauge(designDto.getGauge());
        newDesign.setSetting(designDto.getSetting());
        newDesign.setParent(parent);

        return designRepository.save(newDesign);
    }

    @Override
    public Designs getDesign(Long id) {
        Designs designs = designRepository.findOne(id);
        if (designs == null) {
            throw new RuntimeException("No design available");
        }
        return designs;
    }

    @Override
    @CacheEvict(cacheNames = "designs",allEntries = true)
    public Designs updateDesign(Long id, DesignDto designDto) {
        Designs existingDesign = designRepository.findOne(id);
        if (existingDesign == null) {
            throw new RuntimeException("No design available");
        }


        Customers customers = customerRepository.findOne(designDto.getCustomerId());
        if (customers == null) {
            throw new RuntimeException("No such customer available");
        }


        Designs parent = null;
        Designs duplicateDesign = null;
        if (designDto.getParentId() != null) {
            parent = designRepository.findOne(designDto.getParentId());
            if (parent == null) {
                throw new RuntimeException("No such parent available");
            }
             duplicateDesign = designRepository.findByNameAndParentAndCustomer(designDto.getName(), designDto.getParentId(), designDto.getCustomerId());

        }else {
            duplicateDesign = designRepository.findByNameAndCustomer(designDto.getName(), designDto.getCustomerId());

        }


        if (duplicateDesign != null && !Objects.equals(duplicateDesign.getId(), id)) {
            throw new RuntimeException("Design name already exist for customer");
        }
        existingDesign.setGauge(designDto.getGauge());
        existingDesign.setCustomer(customers);
        existingDesign.setName(designDto.getName());
        existingDesign.setSetting(designDto.getSetting());
        existingDesign.setParent(parent);

        return designRepository.save(existingDesign);
    }

    @Override
    public List<Designs> getDesignByCustomers(Long customerId) {
        Customers customer = customerRepository.findOne(customerId);
        if (customer.getParent() != null) {
            customerId = customer.getParent().getId();
        }
        return designRepository.findParentByCustomers(customerId);
    }

    @Override
    public List<Designs> findByParent(Long parentId) {
        return designRepository.findByParent(parentId);
    }

    @Override
    public List<Designs> findParentDesigns() {
        return designRepository.findParentDesigns();
    }
}
