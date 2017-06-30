package com.pms.app.service;

import com.pms.app.domain.LocationType;
import com.pms.app.domain.Locations;
import com.pms.app.schema.LocationDto;

import java.util.List;

public interface LocationService {
    List<Locations> getLocations(LocationType locationType);

    Locations addLocations(LocationDto locationDto);

    Locations getLocation(Long id);

    Locations editLocation(Long id, LocationDto locationDto);
}
