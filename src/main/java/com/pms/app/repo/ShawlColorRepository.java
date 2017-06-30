package com.pms.app.repo;

import com.pms.app.domain.ShawlColor;

/**
 * Created by arjun on 6/27/2017.
 */
public interface ShawlColorRepository extends AbstractRepository<ShawlColor> {
    ShawlColor findByName(String name);
}
