package com.pms.app.repo;

import com.pms.app.domain.Shawl;

/**
 * Created by arjun on 6/27/2017.
 */
public interface ShawlRepository extends AbstractRepository<Shawl> {
    Shawl findByName(String name);
}
