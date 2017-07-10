package com.pms.app.service;

import com.pms.app.domain.KnitterMachineHistory;
import com.pms.app.schema.KnitterMachineHistoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by arjun on 6/21/2017.
 */
public interface KnitterMachineHistoryService {
    Page<KnitterMachineHistory> getAll(Long knitterId, Long machineId, Date completedDate, Date dateFrom, Date dateTo, Pageable pageable);

    void add(KnitterMachineHistoryDto knitterMachineHistoryDto);

    void getHistoryReport(Long knitterId, Long machineId, Date completedDate, Date dateFrom, Date dateTo, HttpServletResponse httpServletResponse);

    void delete(Long id);

    void edit(Long id, KnitterMachineHistoryDto knitterMachineHistoryDto);

    KnitterMachineHistoryDto get(Long id);
}
