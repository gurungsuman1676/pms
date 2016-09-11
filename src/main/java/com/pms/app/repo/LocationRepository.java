package com.pms.app.repo;

import com.pms.app.domain.Locations;

import java.util.List;

public interface LocationRepository extends AbstractRepository<Locations> {
    Locations findByName(String name);

    List<Locations> findAllByOrderByNameAsc();
}
