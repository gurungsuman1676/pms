package com.pms.app.controller;

import com.pms.app.convert.DesignPropertiesConvert;
import com.pms.app.schema.DesignPropertiesResource;
import com.pms.app.service.DesignPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by arjun on 7/5/2017.
 */

@RestController
@RequestMapping(Routes.DESIGN_PROPERTIES)
public class DesignPropertiesController {
    private final DesignPropertiesConvert designPropertiesConvert;
    private final DesignPropertiesService designPropertiesService;

    @Autowired
    public DesignPropertiesController(DesignPropertiesConvert designPropertiesConvert, DesignPropertiesService designPropertiesService) {
        this.designPropertiesConvert = designPropertiesConvert;
        this.designPropertiesService = designPropertiesService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<DesignPropertiesResource> getShawlProperties(@PathVariable Long id) {
        return designPropertiesConvert.convert(designPropertiesService.getByShawlId(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public void editShawlProperties(@PathVariable Long id, @RequestBody List<DesignPropertiesResource> designPropertiesResources) {
        designPropertiesService.editForShawl(id, designPropertiesResources);
    }
}