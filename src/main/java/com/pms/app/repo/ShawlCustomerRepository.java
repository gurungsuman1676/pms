package com.pms.app.repo;

import com.pms.app.domain.ShawlCustomer;

/**
 * Created by arjun on 6/27/2017.
 */
public interface ShawlCustomerRepository extends AbstractRepository<ShawlCustomer> {
    ShawlCustomer findByName(String name);
}
