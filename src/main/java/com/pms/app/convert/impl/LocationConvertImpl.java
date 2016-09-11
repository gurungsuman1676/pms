package com.pms.app.convert.impl;

import com.pms.app.convert.LocationConvert;
import com.pms.app.domain.Locations;
import com.pms.app.schema.LocationResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class LocationConvertImpl implements LocationConvert {

    @Override
    public List<LocationResource> convert(List<Locations> locations) {
        return locations.stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public LocationResource convert(Locations location) {
        return LocationResource.builder()
                .name(location.getName())
                .id(location.getId())
                .build();
    }
}
