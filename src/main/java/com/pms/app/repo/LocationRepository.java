package com.pms.app.repo;

import com.pms.app.domain.LocationType;
import com.pms.app.domain.Locations;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepository extends AbstractRepository<Locations> {
    Locations findByName(String name);

    @Query("select l from Locations  l where l.locationType =:type order by l.name asc ")
    List<Locations> findAllByLocationTypeOrderByNameAsc(@Param("type") LocationType locationType);

    List<Locations> findAllByOrderByNameAsc();

}
