package com.pms.app.controller;

import com.pms.app.convert.ShawlEntryConvert;
import com.pms.app.domain.ShawlEntry;
import com.pms.app.schema.PageResult;
import com.pms.app.schema.ShawlEntryDto;
import com.pms.app.schema.ShawlEntryResource;
import com.pms.app.service.ShawlEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

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
    public PageResult<ShawlEntryResource> getShawlEntry(
            @RequestParam(value = "locationId", required = false) Long locationId,
            @RequestParam(value = "sizeId", required = false) Long sizeId,
            @RequestParam(value = "yarnId", required = false) Long yarnId,
            @RequestParam(value = "customerId", required = false) Long customerId,
            @RequestParam(value = "colorId", required = false) Long colorId,
            @RequestParam(value = "shawlId", required = false) Long shawlId,
            @RequestParam(required = false, value = "entryDateFrom") Date entryDateFrom,
            @RequestParam(required = false, value = "entryDateTo") Date entryDateTo,
            @RequestParam(required = false, value = "exportDateFrom") Date exportDateFrom,
            @RequestParam(required = false, value = "exportDateTo") Date exportDateTo,
            Pageable pageable) {
        Page<ShawlEntry> page = shawlEntryService.getAll(locationId, sizeId, yarnId, customerId, colorId, shawlId, entryDateFrom, entryDateTo, exportDateFrom, exportDateTo, pageable);
        return new PageResult<>(page.getTotalElements(), page.getSize(), page.getNumber(), shawlEntryConvert.convert(page.getContent()));
    }

    @RequestMapping(method = RequestMethod.POST)
    public void updateShawlEntry(@RequestBody ShawlEntryDto shawlEntryDto) {
        shawlEntryService.update(shawlEntryDto);
    }


    @RequestMapping(method = RequestMethod.GET,value = "/report")
    public void generateExcel(
            @RequestParam(value = "locationId", required = false) Long locationId,
            @RequestParam(value = "sizeId", required = false) Long sizeId,
            @RequestParam(value = "yarnId", required = false) Long yarnId,
            @RequestParam(value = "customerId", required = false) Long customerId,
            @RequestParam(value = "colorId", required = false) Long colorId,
            @RequestParam(value = "shawlId", required = false) Long shawlId,
            @RequestParam(required = false, value = "entryDateFrom") Date entryDateFrom,
            @RequestParam(required = false, value = "entryDateTo") Date entryDateTo,
            @RequestParam(required = false, value = "exportDateFrom") Date exportDateFrom,
            @RequestParam(required = false, value = "exportDateTo") Date exportDateTo,
            HttpServletResponse httpServletResponse) {
        shawlEntryService.generateReport(locationId, sizeId, yarnId, customerId, colorId, shawlId, entryDateFrom, entryDateTo, exportDateFrom, exportDateTo, httpServletResponse);
    }

}
