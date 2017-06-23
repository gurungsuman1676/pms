package com.pms.app.convert;

import com.pms.app.domain.Machine;
import com.pms.app.schema.MachineResource;

import java.util.List;

/**
 * Created by arjun on 6/21/2017.
 */
public interface MachineConvert {
    List<MachineResource> convert(List<Machine> machines);

    MachineResource convert(Machine machine);
}
