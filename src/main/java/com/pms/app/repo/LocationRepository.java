package com.pms.app.repo;

import com.pms.app.domain.LocationType;
import com.pms.app.domain.Locations;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface LocationRepository extends AbstractRepository<Locations> {

    @Query("select l from Locations  l where l.locationType =:type order by l.name asc ")
    List<Locations> findAllByLocationTypeOrderByNameAsc(@Param("type") LocationType locationType);

    List<Locations> findAllByOrderByNameAsc();

    Locations findByNameAndLocationType(String name, LocationType locationType);

    @Query("select l.name from Locations  l where l.locationType =com.pms.app.domain.LocationType.KNITTING and l.name <> 'NO-LOCATION' order by l.order asc ")
    List<String> findAllKnittingLocationOrderByIdAsc();

}
