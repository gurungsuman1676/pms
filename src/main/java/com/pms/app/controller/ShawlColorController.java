package com.pms.app.controller;

import com.pms.app.convert.ShawlColorConvert;
import com.pms.app.schema.ShawlColorDto;
import com.pms.app.schema.ShawlColorResource;
import com.pms.app.service.ShawlColorService;
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
@RequestMapping(Routes.SHAWL_COLOR)
public class ShawlColorController {
    private final ShawlColorConvert shawlColorConvert;
    private final ShawlColorService shawlColorService;

    @Autowired
    public ShawlColorController(ShawlColorConvert shawlColorConvert, ShawlColorService shawlColorService) {
        this.shawlColorConvert = shawlColorConvert;
        this.shawlColorService = shawlColorService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<ShawlColorResource> getShawlColor() {
        return shawlColorConvert.convert(shawlColorService.getAll());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ShawlColorResource addShawlColor(@RequestBody ShawlColorDto shawlColorDto) {
        return shawlColorConvert.convert(shawlColorService.add(shawlColorDto));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ShawlColorResource getShawlColor(@PathVariable Long id) {
        return shawlColorConvert.convert(shawlColorService.get(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ShawlColorResource editShawlColor(@PathVariable Long id, @RequestBody ShawlColorDto shawlColorDto) {
        return shawlColorConvert.convert(shawlColorService.edit(id, shawlColorDto));
    }
}
