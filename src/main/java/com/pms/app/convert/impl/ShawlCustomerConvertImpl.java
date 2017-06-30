package com.pms.app.convert.impl;

import com.pms.app.convert.ShawlCustomerConvert;
import com.pms.app.domain.ShawlCustomer;
import com.pms.app.schema.ShawlCustomerResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by arjun on 6/27/2017.
 */

@Component
public class ShawlCustomerConvertImpl implements ShawlCustomerConvert {
    @Override
    public List<ShawlCustomerResource> convert(List<ShawlCustomer> shawlCustomers) {
        return shawlCustomers.stream().map(this::convert).collect(Collectors.toList());

    }

    @Override
    public ShawlCustomerResource convert(ShawlCustomer shawlCustomer) {
        if (shawlCustomer == null) {
            return null;
        }
        return ShawlCustomerResource.builder().id(shawlCustomer.getId()).name(shawlCustomer.getName()).build();
    }
}
