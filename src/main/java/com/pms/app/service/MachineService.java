package com.pms.app.service;

import com.pms.app.domain.Machine;
import com.pms.app.schema.MachineDto;

import java.util.List;

/**
 * Created by arjun on 6/21/2017.
 */
public interface MachineService {
    List<Machine> getAll();

    Machine add(MachineDto machineDto);

    Machine get(Long id);

    Machine edit(Long id, MachineDto machineDto);
}
