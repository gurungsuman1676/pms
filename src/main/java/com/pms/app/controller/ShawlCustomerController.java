package com.pms.app.controller;

import com.pms.app.convert.ShawlCustomerConvert;
import com.pms.app.schema.ShawlCustomerDto;
import com.pms.app.schema.ShawlCustomerResource;
import com.pms.app.service.ShawlCustomerService;
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
@RequestMapping(Routes.SHAWL_CUSTOMER)
public class ShawlCustomerController {
    private final ShawlCustomerConvert shawlCustomerConvert;
    private final ShawlCustomerService shawlCustomerService;

    @Autowired
    public ShawlCustomerController(ShawlCustomerConvert shawlCustomerConvert, ShawlCustomerService shawlCustomerService) {
        this.shawlCustomerConvert = shawlCustomerConvert;
        this.shawlCustomerService = shawlCustomerService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<ShawlCustomerResource> getShawlCustomer() {
        return shawlCustomerConvert.convert(shawlCustomerService.getAll());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ShawlCustomerResource addShawlCustomer(@RequestBody ShawlCustomerDto shawlCustomerDto) {
        return shawlCustomerConvert.convert(shawlCustomerService.add(shawlCustomerDto));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ShawlCustomerResource getShawlCustomer(@PathVariable Long id) {
        return shawlCustomerConvert.convert(shawlCustomerService.get(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ShawlCustomerResource editShawlCustomer(@PathVariable Long id, @RequestBody ShawlCustomerDto shawlCustomerDto) {
        return shawlCustomerConvert.convert(shawlCustomerService.edit(id, shawlCustomerDto));
    }
}
