package com.pms.app.service.impl;

import com.pms.app.domain.ClothActivity;
import com.pms.app.domain.Clothes;
import com.pms.app.domain.Knitter;
import com.pms.app.domain.KnitterMachineHistory;
import com.pms.app.domain.LocationEnum;
import com.pms.app.domain.LocationType;
import com.pms.app.domain.Locations;
import com.pms.app.domain.Machine;
import com.pms.app.domain.Status;
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
import java.util.List;
import java.util.Objects;

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
        return knitterMachineHistoryRepository.getAll(knitterId, machineId, completedDate, dateFrom, dateTo, pageable);
    }

    @Override
    @Transactional
    public void add(KnitterMachineHistoryDto knitterMachineHistoryDto) {
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
        Clothes providedCloth = clothRepository.findOne(knitterMachineHistoryDto.getClothId());
        if (providedCloth == null) {
            throw new RuntimeException("No such cloth available");
        }

        Locations preKnitting = locationRepository.findByName(LocationEnum.PRE_KNITTING.getName());

        List<Clothes> clothes = clothRepository.findByOrderNoAndCustomerAndPriceAndColorAndTypeAndStatusAndLocation(providedCloth.getOrder_no(),
                providedCloth.getCustomer().getId(),
                providedCloth.getPrice().getId(),
                providedCloth.getColor().getId(),
                providedCloth.getType(),
                Status.ACTIVE.toString(),
                preKnitting.getId(),
                knitterMachineHistoryDto.getQuantity());

        if (!Objects.equals(clothes.size(), knitterMachineHistoryDto.getQuantity())) {
            throw new RuntimeException("Total available cloth for given values in pre knitting is " + clothes.size());
        }

        Locations locations = locationRepository.findByNameAndLocationType(LocationEnum.PRE_KNITTING_COMPLETED.getName(), LocationType.KNITTING);

        clothes.forEach(
                cloth -> {
                    if (cloth.getLocation().getName().equalsIgnoreCase(LocationEnum.PRE_KNITTING.getName())) {
                        cloth.setLocation(locations);
                        cloth = clothRepository.save(cloth);
                    }
                    KnitterMachineHistory knitterMachineHistory = new KnitterMachineHistory();
                    knitterMachineHistory.setCloth(cloth);
                    knitterMachineHistory.setKnitter(knitter);
                    knitterMachineHistory.setMachine(machine);

                    ClothActivity activity = new ClothActivity();
                    activity.setCloth(cloth);
                    activity.setLocation(locations);
                    activity.setUser(AuthUtil.getCurrentUser());

                    clothActivityRepository.save(activity);
                    knitterMachineHistoryRepository.save(knitterMachineHistory);

                }
        );

    }

    @Override
    public void delete(Long id) {
        KnitterMachineHistory knitterMachineHistory = knitterMachineHistoryRepository.findOne(id);
        if (knitterMachineHistory == null) {
            throw new RuntimeException("No history available");
        }
        if (Objects.equals(knitterMachineHistory.getCloth().getLocation().getName(), LocationEnum.PRE_KNITTING_COMPLETED.getName())) {
            Clothes clothes = knitterMachineHistory.getCloth();
            clothes.setLocation(locationRepository.findByName(LocationEnum.PRE_KNITTING.getName()));
            clothes = clothRepository.save(clothes);
            ClothActivity activity = new ClothActivity();
            activity.setCloth(clothes);
            activity.setLocation(clothes.getLocation());
            activity.setUser(AuthUtil.getCurrentUser());
            clothActivityRepository.save(activity);
        }
        knitterMachineHistory.setDeleted(true);
        knitterMachineHistoryRepository.save(knitterMachineHistory);
    }

    @Override
    public void edit(Long id, KnitterMachineHistoryDto knitterMachineHistoryDto) {

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
        KnitterMachineHistory knitterMachineHistory = knitterMachineHistoryRepository.findOne(id);
        if (knitterMachineHistory == null) {
            throw new RuntimeException("No history available");
        }
        knitterMachineHistory.setMachine(machine);
        knitterMachineHistory.setKnitter(knitter);
        knitterMachineHistoryRepository.save(knitterMachineHistory);
    }

    @Override
    public KnitterMachineHistoryDto get(Long id) {
        return knitterMachineHistoryRepository.getById(id);
    }

    @Override
    public void getHistoryReport(Long knitterId, Long machineId, Date completedDate, Date dateFrom, Date dateTo, HttpServletResponse httpServletResponse) {
        knitterHistoryReportService.getHistoryReport(knitterId,machineId,completedDate,dateFrom,dateTo,httpServletResponse);
    }
}
