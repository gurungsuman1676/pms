package com.pms.app.controller;

import com.pms.app.schema.ActivityResource;
import com.pms.app.service.ClothActivityService;
import com.pms.app.service.ShawlActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by arrowhead on 7/16/16.
 */


@RestController
@RequestMapping(value = Routes.SHAWL_ENTRY_ACTIVITY)
public class ShawlActivityController {

    @Autowired
    public ShawlActivityController(ShawlActivityService shawlActivityService) {
        this.shawlActivityService = shawlActivityService;
    }

    private final ShawlActivityService shawlActivityService;


    @RequestMapping(value = "",method = RequestMethod.GET)
    List<ActivityResource> getAllActivity(@PathVariable Long entryId){
       return shawlActivityService.findAllActivityForShawl(entryId);
    }

}
