package com.pms.app.service;

import com.pms.app.schema.ActivityResource;

import java.util.List;

/**
 * Created by arrowhead on 7/16/16.
 */


public interface ShawlActivityService {
    List<ActivityResource> findAllActivityForShawl(Long shawlId);
}
