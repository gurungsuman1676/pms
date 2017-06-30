package com.pms.app.controller;

import com.pms.app.convert.ShawlConvert;
import com.pms.app.schema.ShawlDto;
import com.pms.app.schema.ShawlResource;
import com.pms.app.service.ShawlService;
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
@RequestMapping(Routes.SHAWL)
public class ShawlController {
    private final ShawlConvert shawlConvert;
    private final ShawlService shawlService;

    @Autowired
    public ShawlController(ShawlConvert shawlConvert, ShawlService shawlService) {
        this.shawlConvert = shawlConvert;
        this.shawlService = shawlService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<ShawlResource> getShawl() {
        return shawlConvert.convert(shawlService.getAll());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ShawlResource addShawl(@RequestBody ShawlDto shawlDto) {
        return shawlConvert.convert(shawlService.add(shawlDto));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ShawlResource getShawl(@PathVariable Long id) {
        return shawlConvert.convert(shawlService.get(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ShawlResource editShawl(@PathVariable Long id, @RequestBody ShawlDto shawlDto) {
        return shawlConvert.convert(shawlService.edit(id, shawlDto));
    }
}
