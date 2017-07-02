package com.pms.app.repo.repoCustom;

import com.pms.app.domain.ShawlActivity;
import com.pms.app.schema.ActivityResource;

import java.util.List;

/**
 * Created by arjun on 6/30/2017.
 */
public interface ShawlActivityRepositoryCustom extends AbstractRepositoryCustom<ShawlActivity> {
    List<ActivityResource> findAllActivityForShawl(Long entryId);
}
