package com.pms.app.controller;

import com.pms.app.convert.MachineConvert;
import com.pms.app.schema.MachineDto;
import com.pms.app.schema.MachineResource;
import com.pms.app.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by arjun on 6/21/2017.
 */
@RestController
@RequestMapping(Routes.MACHINE)
public class MachineController {
    private final MachineConvert machineConvert;
    private final MachineService machineService;

    @Autowired
    public MachineController(MachineConvert machineConvert, MachineService machineService) {
        this.machineConvert = machineConvert;
        this.machineService = machineService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<MachineResource> getLocation() {
        return machineConvert.convert(machineService.getAll());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public MachineResource addLocation(@RequestBody MachineDto machineDto) {
        return machineConvert.convert(machineService.add(machineDto));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MachineResource getLocation(@PathVariable Long id) {
        return machineConvert.convert(machineService.get(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public MachineResource editLocation(@PathVariable Long id, @RequestBody MachineDto machineDto) {
        return machineConvert.convert(machineService.edit(id, machineDto));
    }
}
