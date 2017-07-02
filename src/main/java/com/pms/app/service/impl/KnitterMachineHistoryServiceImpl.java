package com.pms.app.service.impl;

import com.pms.app.domain.ClothActivity;
import com.pms.app.domain.Clothes;
import com.pms.app.domain.Knitter;
import com.pms.app.domain.KnitterMachineHistory;
import com.pms.app.domain.LocationEnum;
import com.pms.app.domain.Locations;
import com.pms.app.domain.Machine;
import com.pms.app.repo.ClothActivityRepository;
import com.pms.app.repo.ClothRepository;
import com.pms.app.repo.KnitterMachineHistoryRepository;
import com.pms.app.repo.KnitterRepository;
import com.pms.app.repo.LocationRepository;
import com.pms.app.repo.MachineRepository;
import com.pms.app.schema.KnitterMachineHistoryDto;
import com.pms.app.security.AuthUtil;
import com.pms.app.service.KnitterHistoryReportService;
import com.pms.app.service.KnitterMachineHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by arjun on 6/21/2017.
 */

@Service
public class KnitterMachineHistoryServiceImpl implements KnitterMachineHistoryService {
    private final KnitterMachineHistoryRepository knitterMachineHistoryRepository;
    private final KnitterRepository knitterRepository;
    private final ClothRepository clothRepository;
    private final MachineRepository machineRepository;
    private final LocationRepository locationRepository;
    private final ClothActivityRepository clothActivityRepository;
    private final KnitterHistoryReportService knitterHistoryReportService;

    @Autowired
    public KnitterMachineHistoryServiceImpl(KnitterMachineHistoryRepository knitterMachineHistoryRepository, KnitterRepository knitterRepository, ClothRepository clothRepository, MachineRepository machineRepository, LocationRepository locationRepository, ClothActivityRepository clothActivityRepository, KnitterHistoryReportService knitterHistoryReportService) {
        this.knitterMachineHistoryRepository = knitterMachineHistoryRepository;
        this.knitterRepository = knitterRepository;
        this.clothRepository = clothRepository;
        this.machineRepository = machineRepository;
        this.locationRepository = locationRepository;
        this.clothActivityRepository = clothActivityRepository;
        this.knitterHistoryReportService = knitterHistoryReportService;
    }

    @Override
    public Page<KnitterMachineHistory> getAll(Long knitterId, Long machineId, Date completedDate, Date dateFrom, Date dateTo, Pageable pageable) {
        return knitterMachineHistoryRepository.getAll(knitterId, machineId, completedDate,dateFrom,dateTo, pageable);
    }

    @Override
    @Transactional
    public KnitterMachineHistory add(KnitterMachineHistoryDto knitterMachineHistoryDto) {
        if (knitterMachineHistoryDto.getKnitterId() == null) {
            throw new RuntimeException("No knitter available");
        }
        Knitter knitter = knitterRepository.findOne(knitterMachineHistoryDto.getKnitterId());
        if (knitter == null) {
            throw new RuntimeException("No such knitter available");
        }
        if (knitterMachineHistoryDto.getMachineId() == null) {
            throw new RuntimeException("No Machine available");
        }
        Machine machine = machineRepository.findOne(knitterMachineHistoryDto.getMachineId());
        if (machine == null) {
            throw new RuntimeException("No such machine available");
        }
        if (knitterMachineHistoryDto.getClothId() == null) {
            throw new RuntimeException("No Cloth available");
        }
        Clothes clothes = clothRepository.findOne(knitterMachineHistoryDto.getClothId());
        if (clothes == null) {
            throw new RuntimeException("No such cloth available");
        }
        Locations locations = locationRepository.findByName(LocationEnum.PRE_KNITTING_COMPLETED.getName());
        clothes.setLocation(locations);
        clothes = clothRepository.save(clothes);
        KnitterMachineHistory knitterMachineHistory = new KnitterMachineHistory();
        knitterMachineHistory.setCloth(clothes);
        knitterMachineHistory.setKnitter(knitter);
        knitterMachineHistory.setMachine(machine);

        ClothActivity activity = new ClothActivity();
        activity.setCloth(clothes);
        activity.setLocation(locations);
        activity.setUser(AuthUtil.getCurrentUser());

        clothActivityRepository.save(activity);
        return knitterMachineHistoryRepository.save(knitterMachineHistory);
    }

    @Override
    public void getHistoryReport(Long knitterId, Long machineId, Date completedDate, Date dateFrom, Date dateTo, HttpServletResponse httpServletResponse) {
        knitterHistoryReportService.getHistoryReport(knitterId,machineId,completedDate,dateFrom,dateTo,httpServletResponse);
    }
}
