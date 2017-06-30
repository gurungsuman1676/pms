package com.pms.app.service;

import com.pms.app.domain.ShawlCustomer;
import com.pms.app.schema.ShawlCustomerDto;

import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */
public interface ShawlCustomerService {
    List<ShawlCustomer> getAll();

    ShawlCustomer add(ShawlCustomerDto shawlCustomerDto);

    ShawlCustomer get(Long id);

    ShawlCustomer edit(Long id, ShawlCustomerDto shawlCustomerDto);
}
