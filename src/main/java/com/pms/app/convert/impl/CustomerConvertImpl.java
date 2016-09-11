package com.pms.app.convert.impl;

import com.pms.app.convert.CustomerConvert;
import com.pms.app.domain.Customers;
import com.pms.app.schema.CustomerResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class CustomerConvertImpl implements CustomerConvert {
    @Override
    public List<CustomerResource> convertCustomer(List<Customers> customers) {
        return customers.stream().map(this::convertCustomer).collect(Collectors.toList());
    }

    @Override
    public CustomerResource convertCustomer(Customers customers) {

        if(customers == null){
            return  null;
        }
        return CustomerResource.builder()
                .id(customers.getId())
                .name(customers.getName())
                .currencyName(customers.getCurrency() != null ? customers.getCurrency().getName() : "N/A")
                .currencyId(customers.getCurrency() != null ? customers.getCurrency().getId() : 0L)
                .parentId(customers.getParent() != null ? customers.getParent().getId() : null)
                .parentName(customers.getParent() != null ?customers.getParent().getName() : null)
                .build();
    }
}
