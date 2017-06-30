package com.pms.app.controller;

import com.pms.app.convert.ShawlPropertiesConvert;
import com.pms.app.schema.ShawlPropertiesResource;
import com.pms.app.service.ShawlPropertiesService;
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
@RequestMapping(Routes.SHAWL_PROPERTIES)
public class ShawlPropertiesController {
    private final ShawlPropertiesConvert shawlPropertiesConvert;
    private final ShawlPropertiesService shawlPropertiesService;

    @Autowired
    public ShawlPropertiesController(ShawlPropertiesConvert shawlPropertiesConvert, ShawlPropertiesService shawlPropertiesService) {
        this.shawlPropertiesConvert = shawlPropertiesConvert;
        this.shawlPropertiesService = shawlPropertiesService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<ShawlPropertiesResource> getShawlProperties(@PathVariable Long id) {
        return shawlPropertiesConvert.convert(shawlPropertiesService.getByShawlId(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void editShawlProperties(@PathVariable Long id, @RequestBody List<ShawlPropertiesResource> shawlPropertiesResources) {
        shawlPropertiesService.editForShawl(id, shawlPropertiesResources);
    }
}
