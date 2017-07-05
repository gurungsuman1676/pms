package com.pms.app.service.impl;

import com.pms.app.domain.DesignProperties;
import com.pms.app.domain.Designs;
import com.pms.app.repo.DesignPropertiesRepository;
import com.pms.app.schema.DesignPropertiesResource;
import com.pms.app.service.DesignPropertiesService;
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
public class DesignPropertiesServiceImpl implements DesignPropertiesService {

    private final DesignPropertiesRepository designPropertiesRepository;
    private final EntityManager entityManager;

    @Autowired
    public DesignPropertiesServiceImpl(DesignPropertiesRepository designPropertiesRepository, EntityManager entityManager) {
        this.designPropertiesRepository = designPropertiesRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<DesignProperties> getByShawlId(Long id) {
        return designPropertiesRepository.findAllByDesignIdAndActive(id, true);
    }

    @Override
    public void editForShawl(Long id, List<DesignPropertiesResource> designPropertiesResources) {
        List<DesignProperties> designProperties = designPropertiesRepository.findAllByDesignIdAndActive(id, true);
        Map<Long, DesignProperties> designPropertiesMap = designProperties
                .stream()
                .collect(Collectors.toMap(DesignProperties::getId, Function.identity()));
        designPropertiesResources.forEach(s -> {
            DesignProperties properties = s.getId() == null ? new DesignProperties() : designPropertiesMap.get(s.getId());
            properties.setName(s.getName());
            properties.setDesign(entityManager.getReference(Designs.class, id));
            properties.setValue(s.getValue());
            designPropertiesRepository.save(properties);
            if (s.getId() != null) {
                designPropertiesMap.remove(s.getId());
            }
        });
        designPropertiesMap.keySet().forEach(k -> {
                    DesignProperties deleted = designPropertiesMap.get(k);
                    deleted.setActive(false);
                    designPropertiesRepository.save(deleted);
                }
        );
    }
}
