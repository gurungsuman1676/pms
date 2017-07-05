package com.pms.app.controller;

import com.pms.app.convert.SizeConvert;
import com.pms.app.schema.SizeDto;
import com.pms.app.schema.SizeResource;
import com.pms.app.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = Routes.SIZE)
public class SizeController {

    private final SizeService sizeService;
    private final SizeConvert sizeConvert;

    @Autowired
    public SizeController(SizeService sizeService, SizeConvert sizeConvert) {
        this.sizeService = sizeService;
        this.sizeConvert = sizeConvert;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<SizeResource> getSizes(@RequestParam(required = false,value = "designId") Long designId
            , @RequestParam(required = false ,value = "yarnId") Long yarnId) {
        return sizeConvert.convertSizes(sizeService.getSizes(designId,yarnId));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public SizeResource addSize(@RequestBody SizeDto sizeDto) {
        return sizeConvert.convertSize(sizeService.addSize(sizeDto));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public SizeResource getSize(@PathVariable Long id) {
        return sizeConvert.convertSize(sizeService.getSize(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public SizeResource editSize(@PathVariable Long id, @RequestBody SizeDto sizeDto) {
        return sizeConvert.convertSize(sizeService.updateSize(id, sizeDto));
    }

}
