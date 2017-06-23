package com.pms.app.convert.impl;

import com.pms.app.convert.MachineConvert;
import com.pms.app.domain.Machine;
import com.pms.app.schema.MachineResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by arjun on 6/21/2017.
 */

@Component
public class MachineConvertImpl implements MachineConvert {
    @Override
    public List<MachineResource> convert(List<Machine> machines) {
        return machines.stream().map(this::convert).collect(Collectors.toList());

    }

    @Override
    public MachineResource convert(Machine machine) {
        if (machine == null) {
            return null;
        }
        return MachineResource.builder().id(machine.getId()).name(machine.getName()).build();
    }
}
