package com.pms.app.controller;


import com.pms.app.convert.YarnConvert;
import com.pms.app.schema.YarnDto;
import com.pms.app.schema.YarnResource;
import com.pms.app.service.YarnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(Routes.YARNS)
public class YarnController {

    private final YarnService yarnService;
    private final YarnConvert yarnConvert;

    public static final String YARN_CUSTOMER_ID = "/customers"+"/{customerId}";

    @Autowired
    public YarnController(YarnService yarnService, YarnConvert yarnConvert) {
        this.yarnService = yarnService;
        this.yarnConvert = yarnConvert;
    }

    @RequestMapping(value="", method = RequestMethod.GET)
    public List<YarnResource> getYarns(){
        return yarnConvert.convert(yarnService.getYarns());
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public YarnResource addYarn(@RequestBody YarnDto yarnDto){
        return yarnConvert.convert(yarnService.addYarn(yarnDto));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public YarnResource getYarn(@PathVariable Long id){
        return yarnConvert.convert(yarnService.getYarn(id));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public YarnResource editYarn(@PathVariable Long id,@RequestBody YarnDto yarnDto){
        return yarnConvert.convert(yarnService.updateYarn(id, yarnDto));
    }

//    @RequestMapping(value = YARN_CUSTOMER_ID, method  = RequestMethod.GET)
//    public List<YarnResource> getYarnsByCustomerId(@PathVariable Long customerId){
//       return yarnConvert.convert(yarnService.getYarnByCustomersId(customerId));
//    }

}
