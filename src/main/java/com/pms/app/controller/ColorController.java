package com.pms.app.controller;

import com.pms.app.convert.ColorConvert;
import com.pms.app.schema.ColorDto;
import com.pms.app.schema.ColorResource;
import com.pms.app.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = Routes.COLOR)
public class ColorController {

    private final ColorService colorService;
    private final ColorConvert colorConvert;

    private static final String COLOR_YARN_ID = "/yarns"+"/{yarnId}";


    @Autowired
    public ColorController(ColorService colorService, ColorConvert colorConvert) {
        this.colorService = colorService;
        this.colorConvert = colorConvert;
    }

    @RequestMapping(value="", method = RequestMethod.GET)
    public List<ColorResource> getColors(){
        return colorConvert.convert(colorService.getColors());
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public ColorResource addColor(@RequestBody ColorDto colorDto){
        return colorConvert.convert(colorService.addColor(colorDto));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ColorResource getColor(@PathVariable Long id){
        return colorConvert.convert(colorService.getColor(id));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ColorResource editColor(@PathVariable Long id,@RequestBody ColorDto colorDto){
        return colorConvert.convert(colorService.editColor(id, colorDto));
    }

    @RequestMapping(value = COLOR_YARN_ID, method = RequestMethod.GET)
    public List<ColorResource> getColorByYarn(@PathVariable Long yarnId){
        return colorConvert.convert(colorService.getColorsByYarnId(yarnId));

    }
}
