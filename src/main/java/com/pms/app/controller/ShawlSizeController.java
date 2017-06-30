package com.pms.app.controller;

import com.pms.app.convert.ShawlSizeConvert;
import com.pms.app.schema.ShawlSizeDto;
import com.pms.app.schema.ShawlSizeResource;
import com.pms.app.service.ShawlSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */

@RestController
@RequestMapping(Routes.SHAWL_SIZE)
public class ShawlSizeController {
    private final ShawlSizeConvert shawlSizeConvert;
    private final ShawlSizeService shawlSizeService;

    @Autowired
    public ShawlSizeController(ShawlSizeConvert shawlSizeConvert, ShawlSizeService shawlSizeService) {
        this.shawlSizeConvert = shawlSizeConvert;
        this.shawlSizeService = shawlSizeService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<ShawlSizeResource> getShawlSize() {
        return shawlSizeConvert.convert(shawlSizeService.getAll());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ShawlSizeResource addShawlSize(@RequestBody ShawlSizeDto shawlSizeDto) {
        return shawlSizeConvert.convert(shawlSizeService.add(shawlSizeDto));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ShawlSizeResource getShawlSize(@PathVariable Long id) {
        return shawlSizeConvert.convert(shawlSizeService.get(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ShawlSizeResource editShawlSize(@PathVariable Long id, @RequestBody ShawlSizeDto shawlSizeDto) {
        return shawlSizeConvert.convert(shawlSizeService.edit(id, shawlSizeDto));
    }
}
