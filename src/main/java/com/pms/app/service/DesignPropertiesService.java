package com.pms.app.service;

import com.pms.app.domain.DesignProperties;
import com.pms.app.schema.DesignPropertiesResource;

import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */
public interface DesignPropertiesService {
    List<DesignProperties> getByShawlId(Long id);

    void editForShawl(Long id, List<DesignPropertiesResource> designPropertiesResources);
}
