package com.pms.app.controller;

import com.pms.app.convert.LocationConvert;
import com.pms.app.schema.LocationDto;
import com.pms.app.schema.LocationResource;
import com.pms.app.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = Routes.LOCATION)
public class LocationController {
    
    private final LocationConvert locationConvert;
    private final LocationService locationService;
    @Autowired
    public LocationController(LocationConvert locationConvert, LocationService locationService) {
        this.locationConvert = locationConvert;
        this.locationService = locationService;
    }

    @RequestMapping(value="", method = RequestMethod.GET)
    public List<LocationResource> getLocation(){
        return locationConvert.convert(locationService.getLocations());
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public LocationResource addLocation(@RequestBody LocationDto locationDto){
        return locationConvert.convert(locationService.addLocations(locationDto));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public LocationResource getLocation(@PathVariable Long id){
        return locationConvert.convert(locationService.getLocation(id));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public LocationResource editLocation(@PathVariable Long id,@RequestBody LocationDto locationDto){
        return locationConvert.convert(locationService.editLocation(id, locationDto));
    }
}