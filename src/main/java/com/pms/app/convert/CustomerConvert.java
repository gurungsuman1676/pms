package com.pms.app.convert;

import com.pms.app.domain.Customers;
import com.pms.app.schema.CustomerResource;

import java.util.List;

public interface CustomerConvert {
    List<CustomerResource> convertCustomer(List<Customers> customers);

    CustomerResource convertCustomer(Customers customers);
}
