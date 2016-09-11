package com.pms.app.controller;


import com.pms.app.convert.DesignConvert;
import com.pms.app.schema.DesignDto;
import com.pms.app.schema.DesignResource;
import com.pms.app.schema.PrintDto;
import com.pms.app.schema.PrintResource;
import com.pms.app.service.DesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = Routes.DESIGN)
public class DesignController {
    private static final String DESIGN_PARENT ="/parent";
    private static final String DESIGN_PARENT_CUSTOMER_ID =DESIGN_PARENT+"/customer" + "/{customerId}";
    private static final String DESIGN_PARENT_ID =  DESIGN_PARENT+ "/{id}";

    private final DesignService designService;
    private final DesignConvert designConvert;


    @Autowired
    public DesignController(DesignService designService, DesignConvert designConvert) {
        this.designService = designService;
        this.designConvert = designConvert;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<DesignResource> getDesigns() {
        return designConvert.convert(designService.getDesigns());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public DesignResource addDesign(@RequestBody DesignDto designDto) {
        return designConvert.convert(designService.addDesign(designDto));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DesignResource getDesign(@PathVariable Long id) {
        return designConvert.convert(designService.getDesign(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public DesignResource updateDesign(@PathVariable Long id, @RequestBody DesignDto designDto) {
        return designConvert.convert(designService.updateDesign(id, designDto));
    }

    @RequestMapping(value = DESIGN_PARENT_CUSTOMER_ID, method = RequestMethod.GET)
    public List<DesignResource> getDesignByCustomers(@PathVariable Long customerId) {
        return designConvert.convert(designService.getDesignByCustomers(customerId));
    }

    @RequestMapping(value = DESIGN_PARENT_ID,method = RequestMethod.GET)
    public List<DesignResource> findByParent(@PathVariable Long parentId) {
        return designConvert.convert(designService.findByParent(parentId));
    }

    @RequestMapping(value = DESIGN_PARENT,method = RequestMethod.GET)
    public List<DesignResource> getParentDesign(){
        return designConvert.convert(designService.findParentDesigns());
    }
}

