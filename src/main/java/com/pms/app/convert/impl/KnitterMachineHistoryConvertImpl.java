package com.pms.app.convert.impl;

import com.pms.app.convert.ClothConvert;
import com.pms.app.convert.KnitterConvert;
import com.pms.app.convert.KnitterMachineHistoryConvert;
import com.pms.app.convert.MachineConvert;
import com.pms.app.domain.KnitterMachineHistory;
import com.pms.app.repo.KnitterMachineHistoryRepository;
import com.pms.app.schema.KnitterMachineHistoryResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by arjun on 6/21/2017.
 */

@Component
public class KnitterMachineHistoryConvertImpl implements KnitterMachineHistoryConvert {

    private final ClothConvert clothConvert;
    private final MachineConvert machineConvert;
    private final KnitterConvert knitterConvert;

    @Autowired
    public KnitterMachineHistoryConvertImpl(ClothConvert clothConvert, MachineConvert machineConvert, KnitterConvert knitterConvert) {
        this.clothConvert = clothConvert;
        this.machineConvert = machineConvert;
        this.knitterConvert = knitterConvert;
    }

    @Override
    public List<KnitterMachineHistoryResource> convert(List<KnitterMachineHistory> knitterMachineHistories) {
        return knitterMachineHistories.stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public KnitterMachineHistoryResource convert(KnitterMachineHistory knitterMachineHistory) {
        return KnitterMachineHistoryResource.builder().cloth(clothConvert.convert(knitterMachineHistory.getCloth()))
                .knitter(knitterConvert.convert(knitterMachineHistory.getKnitter()))
                .machine(machineConvert.convert(knitterMachineHistory.getMachine()))
                .id(knitterMachineHistory.getId())
                .created(knitterMachineHistory.getCreated())
                .build();
    }
}
