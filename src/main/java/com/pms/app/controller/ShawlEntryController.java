package com.pms.app.controller;

import com.pms.app.convert.ShawlEntryConvert;
import com.pms.app.domain.ShawlEntry;
import com.pms.app.schema.PageResult;
import com.pms.app.schema.ShawlEntryDto;
import com.pms.app.schema.ShawlEntryResource;
import com.pms.app.service.ShawlEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by arjun on 6/27/2017.
 */

@RestController
@RequestMapping(value = Routes.SHAWL_ENTRY)
public class ShawlEntryController {
    private final ShawlEntryConvert shawlEntryConvert;
    private final ShawlEntryService shawlEntryService;

    @Autowired
    public ShawlEntryController(ShawlEntryConvert shawlEntryConvert, ShawlEntryService shawlEntryService) {
        this.shawlEntryConvert = shawlEntryConvert;
        this.shawlEntryService = shawlEntryService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public PageResult<ShawlEntryResource> getShawlEntry() {
        Page<ShawlEntry> page = shawlEntryService.getAll();
        return new PageResult<>(page.getTotalElements(), page.getSize(), page.getNumber(), shawlEntryConvert.convert(page.getContent()));
    }

    @RequestMapping(method = RequestMethod.POST)
    public void updateShawlEntry(@RequestBody ShawlEntryDto shawlEntryDto) {
        shawlEntryService.update(shawlEntryDto);
    }

}
