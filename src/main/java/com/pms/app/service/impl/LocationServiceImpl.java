package com.pms.app.service.impl;

import com.pms.app.domain.Locations;
import com.pms.app.repo.LocationRepository;
import com.pms.app.schema.LocationDto;
import com.pms.app.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    @Cacheable(cacheNames = "locations")
    public List<Locations> getLocations() {
        return  locationRepository.findAllByOrderByNameAsc();
    }

    @Override
    @CacheEvict(cacheNames = "locations",allEntries = true)
    public Locations addLocations(LocationDto locationDto) {

        Locations locations = locationRepository.findByName(locationDto.getName());
        if(locations != null){
            throw new RuntimeException("Location already exists");
        }
        Locations newLocations = new Locations();
        newLocations.setName(locationDto.getName());
        return locationRepository.save(newLocations);
    }

    @Override
    public Locations getLocation(Long id) {

        Locations oldLocations = locationRepository.findOne(id);

        if(oldLocations == null){
            throw new RuntimeException("No such location available");
        }
        return oldLocations;
    }

    @Override
    @CacheEvict(cacheNames = "locations",allEntries = true)
    public Locations editLocation(Long id, LocationDto locationDto) {
        Locations locations = locationRepository.findByName(locationDto.getName());
        if(locations != null){
            throw new RuntimeException("Location already exists");
        }
        Locations oldLocations = locationRepository.findOne(id);
        if(oldLocations == null){
            throw new RuntimeException("No such location available");
        }
        oldLocations.setName(locationDto.getName());
        return locationRepository.save(oldLocations);
    }
}
