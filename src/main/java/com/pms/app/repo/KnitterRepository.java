package com.pms.app.repo;

import com.pms.app.domain.Knitter;

/**
 * Created by arjun on 6/21/2017.
 */
public interface KnitterRepository extends AbstractRepository<Knitter> {
    Knitter findByName(String name);
}
