package com.pms.app.repo;

import com.pms.app.domain.ShawlYarn;

/**
 * Created by arjun on 6/27/2017.
 */
public interface ShawlYarnRepository extends AbstractRepository<ShawlYarn> {
    ShawlYarn findByName(String name);
}
