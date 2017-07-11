package com.pms.app.repo.repoCustom;

import com.pms.app.domain.KnitterMachineHistory;
import com.pms.app.schema.KnitterHistoryReportResource;
import com.pms.app.schema.KnitterMachineHistoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * Created by arjun on 6/21/2017.
 */
public interface KnitterMachineHistoryRepositoryCustom extends AbstractRepositoryCustom<KnitterMachineHistory> {
    Page<KnitterMachineHistory> getAll(Long knitterId, Long machineId, Date completedDate, Date dateFrom, Date dateTo, Pageable pageable);

    List<KnitterHistoryReportResource> getAllResource(Long knitterId, Long machineId, Date completedDate, Date dateFrom, Date dateTo);

    KnitterMachineHistoryDto getById(Long id);
}
