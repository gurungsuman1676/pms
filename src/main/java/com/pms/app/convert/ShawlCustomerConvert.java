package com.pms.app.convert;

import com.pms.app.domain.ShawlCustomer;
import com.pms.app.schema.ShawlCustomerResource;

import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */
public interface ShawlCustomerConvert {
    List<ShawlCustomerResource> convert(List<ShawlCustomer> shawlCustomers);

    ShawlCustomerResource convert(ShawlCustomer shawlCustomer);
}
