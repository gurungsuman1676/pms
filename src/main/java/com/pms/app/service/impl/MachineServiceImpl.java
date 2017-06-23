package com.pms.app.service.impl;

import com.pms.app.domain.Machine;
import com.pms.app.repo.MachineRepository;
import com.pms.app.schema.MachineDto;
import com.pms.app.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by arjun on 6/21/2017.
 */

@Service
public class MachineServiceImpl implements MachineService {
    private final MachineRepository machineRepository;

    @Autowired
    public MachineServiceImpl(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    @Override
    public List<Machine> getAll() {
        return (List<Machine>) machineRepository.findAll();
    }

    @Override
    public Machine add(MachineDto machineDto) {
        if (machineDto.getName() == null) {
            throw new RuntimeException("Machine name not available");
        }
        Machine existingMachine = machineRepository.findByName(machineDto.getName());
        if (existingMachine != null) {
            throw new RuntimeException("Machine with name " + machineDto.getName() + " already exist");
        }
        Machine machine = new Machine();
        machine.setName(machineDto.getName());
        return machineRepository.save(machine);
    }

    @Override
    public Machine get(Long id) {
        Machine machine = machineRepository.findOne(id);
        if (machine == null) {
            throw new RuntimeException("No machine found ");
        }
        return machine;
    }

    @Override
    public Machine edit(Long id, MachineDto machineDto) {
        if (machineDto.getName() == null) {
            throw new RuntimeException("Machine name not available");
        }
        Machine existingMachine = machineRepository.findByName(machineDto.getName());
        if (existingMachine != null) {
            throw new RuntimeException("Machine with same name " + machineDto.getName() + " already exist");
        }
        Machine machine = machineRepository.findOne(id);
        machine.setName(machineDto.getName());
        return machineRepository.save(machine);
    }
}
