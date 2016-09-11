package com.pms.app.service;

import com.pms.app.domain.Designs;
import com.pms.app.schema.DesignDto;

import java.util.List;

public interface DesignService {
    List<Designs> getDesigns();

    Designs addDesign(DesignDto designDto);

    Designs getDesign(Long id);

    Designs updateDesign(Long id, DesignDto designDto);

    List<Designs> getDesignByCustomers(Long customerId);

    List<Designs> findByParent(Long parentId);

    List<Designs> findParentDesigns();

}
