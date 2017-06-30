package com.pms.app.service.impl;

import com.pms.app.domain.Shawl;
import com.pms.app.domain.ShawlProperties;
import com.pms.app.repo.ShawlPropertiesRepository;
import com.pms.app.schema.ShawlPropertiesResource;
import com.pms.app.service.ShawlPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by arjun on 6/27/2017.
 */

@Service
public class ShawlPropertiesServiceImpl implements ShawlPropertiesService {

    private final ShawlPropertiesRepository shawlPropertiesRepository;
    private final EntityManager entityManager;

    @Autowired
    public ShawlPropertiesServiceImpl(ShawlPropertiesRepository shawlPropertiesRepository, EntityManager entityManager) {
        this.shawlPropertiesRepository = shawlPropertiesRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<ShawlProperties> getByShawlId(Long id) {
        return shawlPropertiesRepository.findAllByShawlIdAndActive(id, true);
    }

    @Override
    public void editForShawl(Long id, List<ShawlPropertiesResource> shawlPropertiesResources) {
        List<ShawlProperties> shawlProperties = shawlPropertiesRepository.findAllByShawlIdAndActive(id, true);
        Map<Long, ShawlProperties> shawlPropertiesMap = shawlProperties
                .stream()
                .collect(Collectors.toMap(ShawlProperties::getId, Function.identity()));
        shawlPropertiesResources.forEach(s -> {
            ShawlProperties properties = s.getId() == null ? new ShawlProperties() : shawlPropertiesMap.get(s.getId());
            properties.setName(s.getName());
            properties.setShawl(entityManager.getReference(Shawl.class, id));
            properties.setValue(s.getValue());
            shawlPropertiesRepository.save(properties);
            if (s.getId() != null) {
                shawlPropertiesMap.remove(s.getId());
            }
        });
        shawlPropertiesMap.keySet().forEach(k -> {
                    ShawlProperties deleted = shawlPropertiesMap.get(k);
                    deleted.setActive(false);
                    shawlPropertiesRepository.save(deleted);
                }
        );
    }
}
