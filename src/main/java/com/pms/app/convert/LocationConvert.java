package com.pms.app.convert;

import com.pms.app.domain.Locations;
import com.pms.app.schema.LocationResource;

import java.util.List;

public interface LocationConvert {
    List<LocationResource> convert(List<Locations> locations);

    LocationResource convert(Locations location);
}
