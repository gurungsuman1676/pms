package com.pms.app.controller;

import com.pms.app.schema.ActivityResource;
import com.pms.app.service.ClothActivityService;
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
@RequestMapping(value = Routes.ACTIVITY)
public class ClothActivityController {

    @Autowired
    public ClothActivityController(ClothActivityService clothActivityService) {
        this.clothActivityService = clothActivityService;
    }

    private final ClothActivityService clothActivityService;


    @RequestMapping(value = "",method = RequestMethod.GET)
    List<ActivityResource> getAllActivity(@PathVariable Long clothId){
       return clothActivityService.findAllActivityForClothes(clothId);
    }

}
