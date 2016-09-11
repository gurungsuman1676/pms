package com.pms.app.service;

import com.pms.app.domain.Customers;
import com.pms.app.schema.CustomerDto;

import java.util.List;

public interface CustomerService {
    List<Customers> getCustomers();

    Customers addCustomer(CustomerDto customerDto);

    Customers getCustomer(Long id);

    Customers updateCustomer(Long id, CustomerDto customerDto);

    List<Customers> findParentCustomers();

    List<Customers> getCustomerByParentId(Long parentId);
}
