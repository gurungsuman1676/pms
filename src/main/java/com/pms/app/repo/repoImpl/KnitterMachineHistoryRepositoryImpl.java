package com.pms.app.repo.repoImpl;

import com.mysema.query.BooleanBuilder;
import com.pms.app.domain.KnitterMachineHistory;
import com.pms.app.domain.QKnitterMachineHistory;
import com.pms.app.repo.KnitterMachineHistoryRepository;
import com.pms.app.repo.repoCustom.KnitterMachineHistoryRepositoryCustom;
import com.pms.app.schema.KnitterHistoryReportResource;
import com.pms.app.schema.KnitterMachineHistoryDto;
import com.pms.app.schema.QKnitterHistoryReportResource;
import com.pms.app.schema.QKnitterMachineHistoryDto;
import com.pms.app.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by arjun on 6/21/2017.
 */
public class KnitterMachineHistoryRepositoryImpl extends AbstractRepositoryImpl<KnitterMachineHistory, KnitterMachineHistoryRepository> implements KnitterMachineHistoryRepositoryCustom {
    public KnitterMachineHistoryRepositoryImpl() {
        super(KnitterMachineHistory.class);
    }

    @Autowired
    @Lazy
    public void setRepository(KnitterMachineHistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<KnitterMachineHistory> getAll(Long knitterId, Long machineId, Date completedDate, Date dateFrom, Date dateTo, Pageable pageable) {
        QKnitterMachineHistory machineHistory = QKnitterMachineHistory.knitterMachineHistory;
        BooleanBuilder where = new BooleanBuilder();
        if (knitterId != null) {
            where.and(machineHistory.knitter.id.eq(knitterId));
        }

        if (machineId != null) {
            where.and(machineHistory.machine.id.eq(machineId));
        }

        if (dateFrom != null) {
            where.and(machineHistory.created.goe(dateFrom));
        }
        if (dateTo != null) {
            where.and(machineHistory.created.loe(DateUtils.addDays(dateTo,1)));
        }
        where.and(machineHistory.deleted.isFalse());

        if (completedDate != null) {
            Calendar gval = Calendar.getInstance();
            gval.setTime(completedDate);

            gval.set(Calendar.HOUR_OF_DAY, 0);
            gval.set(Calendar.MINUTE, 0);
            gval.set(Calendar.SECOND, 0);
            gval.set(Calendar.MILLISECOND, 0);
            Date startDate = gval.getTime();
            gval.add(Calendar.DAY_OF_MONTH, 1);
            Date endTime = gval.getTime();
            where.and(machineHistory.created.between(startDate, endTime));
        }
        return repository.findAll(where, pageable);
    }

    @Override
    public List<KnitterHistoryReportResource> getAllResource(Long knitterId, Long machineId, Date completedDate, Date dateFrom, Date dateTo) {
        QKnitterMachineHistory machineHistory = QKnitterMachineHistory.knitterMachineHistory;
        BooleanBuilder where = new BooleanBuilder();
        if (knitterId != null) {
            where.and(machineHistory.knitter.id.eq(knitterId));
        }

        if (machineId != null) {
            where.and(machineHistory.machine.id.eq(machineId));
        }

        if (dateFrom != null) {
            where.and(machineHistory.created.goe(dateFrom));
        }
        if (dateTo != null) {
            where.and(machineHistory.created.loe(DateUtils.addDays(dateTo,1)));
        }
        if (completedDate != null) {
            Calendar gval = Calendar.getInstance();
            gval.setTime(completedDate);

            gval.set(Calendar.HOUR_OF_DAY, 0);
            gval.set(Calendar.MINUTE, 0);
            gval.set(Calendar.SECOND, 0);
            gval.set(Calendar.MILLISECOND, 0);
            Date startDate = gval.getTime();
            gval.add(Calendar.DAY_OF_MONTH, 1);
            Date endTime = gval.getTime();
            where.and(machineHistory.created.between(startDate, endTime));
        }
        where.and(machineHistory.deleted.isFalse());

        return from(machineHistory)
                .innerJoin(machineHistory.cloth)
                .innerJoin(machineHistory.cloth.price)
                .innerJoin(machineHistory.cloth.price.design)
                .innerJoin(machineHistory.cloth.price.size)
                .leftJoin(machineHistory.cloth.price.yarn)
                .where(where).list(
                new QKnitterHistoryReportResource(machineHistory.created,
                        machineHistory.cloth.deliver_date,
                        machineHistory.knitter.name,
                        machineHistory.machine.name,
                        machineHistory.cloth.order_no,
                        machineHistory.cloth.customer.name,
                        machineHistory.cloth.price.design.name,
                        machineHistory.cloth.price.yarn.name,
                        machineHistory.cloth.price.size.name,
                        machineHistory.cloth.price.design.gauge,
                        machineHistory.cloth.price.design.setting,
                        machineHistory.cloth.reOrder,
                        machineHistory.cloth.color.code)
        );
    }

    @Override
    public KnitterMachineHistoryDto getById(Long id) {
        QKnitterMachineHistory knitterMachineHistory = QKnitterMachineHistory.knitterMachineHistory;
        return from(knitterMachineHistory)
                .where(knitterMachineHistory.id.eq(id))
                .singleResult(new QKnitterMachineHistoryDto(knitterMachineHistory.knitter.id,knitterMachineHistory.machine.id));
    }
}
