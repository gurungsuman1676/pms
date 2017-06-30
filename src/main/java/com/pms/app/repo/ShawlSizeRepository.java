package com.pms.app.repo;

import com.pms.app.domain.ShawlSize;

/**
 * Created by arjun on 6/27/2017.
 */
public interface ShawlSizeRepository extends AbstractRepository<ShawlSize> {
    ShawlSize findByName(String name);
}
