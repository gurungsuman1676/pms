package com.pms.app.controller;


import com.pms.app.convert.CustomerConvert;
import com.pms.app.schema.CustomerDto;
import com.pms.app.schema.CustomerResource;
import com.pms.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Routes.CUSTOMER)
public class CustomerController {


    private final CustomerService customerService;
    private final CustomerConvert customerConvert;
    public static final String CUSTOMER_PARENT = "/parent";
    public static final String CUSTOMER_PARENT_ID = CUSTOMER_PARENT+"/{parentId}";


    @Autowired
    public CustomerController(CustomerService customerService, CustomerConvert customerConvert) {
        this.customerService = customerService;
        this.customerConvert = customerConvert;
    }


    @RequestMapping(value="", method = RequestMethod.GET)
    public List<CustomerResource> getCustomers(){
        return customerConvert.convertCustomer(customerService.getCustomers());
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public CustomerResource addCustomer(@RequestBody CustomerDto customerDto){
        return customerConvert.convertCustomer(customerService.addCustomer(customerDto));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public CustomerResource getCustomer(@PathVariable Long id){
        return customerConvert.convertCustomer(customerService.getCustomer(id));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public CustomerResource editCustomer(@PathVariable Long id,@RequestBody CustomerDto customerDto){
        return customerConvert.convertCustomer(customerService.updateCustomer(id, customerDto));
    }

    @RequestMapping(value = CUSTOMER_PARENT,method = RequestMethod.GET)
    public List<CustomerResource> getParentCustomers(){
        return customerConvert.convertCustomer(customerService.findParentCustomers());
    }
    @RequestMapping(value = CUSTOMER_PARENT_ID,method = RequestMethod.GET)
    public List<CustomerResource> getCustomerByParentId(@PathVariable Long parentId){
        return customerConvert.convertCustomer(customerService.getCustomerByParentId(parentId));
    }

}
