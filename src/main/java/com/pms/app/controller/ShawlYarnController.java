package com.pms.app.controller;

import com.pms.app.convert.ShawlYarnConvert;
import com.pms.app.schema.ShawlYarnDto;
import com.pms.app.schema.ShawlYarnResource;
import com.pms.app.service.ShawlYarnService;
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
@RequestMapping(Routes.SHAWL_YARN)
public class ShawlYarnController {
    private final ShawlYarnConvert shawlYarnConvert;
    private final ShawlYarnService shawlYarnService;

    @Autowired
    public ShawlYarnController(ShawlYarnConvert shawlYarnConvert, ShawlYarnService shawlYarnService) {
        this.shawlYarnConvert = shawlYarnConvert;
        this.shawlYarnService = shawlYarnService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<ShawlYarnResource> getShawlYarn() {
        return shawlYarnConvert.convert(shawlYarnService.getAll());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ShawlYarnResource addShawlYarn(@RequestBody ShawlYarnDto shawlYarnDto) {
        return shawlYarnConvert.convert(shawlYarnService.add(shawlYarnDto));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ShawlYarnResource getShawlYarn(@PathVariable Long id) {
        return shawlYarnConvert.convert(shawlYarnService.get(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ShawlYarnResource editShawlYarn(@PathVariable Long id, @RequestBody ShawlYarnDto shawlYarnDto) {
        return shawlYarnConvert.convert(shawlYarnService.edit(id, shawlYarnDto));
    }
}
