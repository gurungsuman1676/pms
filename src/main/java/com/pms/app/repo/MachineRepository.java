package com.pms.app.repo;

import com.pms.app.domain.Machine;

/**
 * Created by arjun on 6/21/2017.
 */
public interface MachineRepository extends AbstractRepository<Machine> {
    Machine findByName(String name);
}
