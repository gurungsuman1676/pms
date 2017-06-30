package com.pms.app.service;

import com.pms.app.domain.ShawlProperties;
import com.pms.app.schema.ShawlPropertiesResource;

import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */
public interface ShawlPropertiesService {
    List<ShawlProperties> getByShawlId(Long id);

    void editForShawl(Long id, List<ShawlPropertiesResource> shawlPropertiesResources);
}
