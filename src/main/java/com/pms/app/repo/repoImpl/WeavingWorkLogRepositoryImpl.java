package com.pms.app.repo.repoImpl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.pms.app.domain.QWeavingWorkLog;
import com.pms.app.domain.WeavingWorkLog;
import com.pms.app.repo.WeavingWorkLogRepository;
import com.pms.app.repo.repoCustom.WeavingWorkLogRepositoryCustom;
import com.pms.app.schema.PageResult;
import com.pms.app.schema.QWeavingLogResource;
import com.pms.app.schema.WeavingLogResource;
import com.pms.app.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * Created by arjun on 7/11/2017.
 */
public class WeavingWorkLogRepositoryImpl extends AbstractRepositoryImpl<WeavingWorkLog, WeavingWorkLogRepository> implements WeavingWorkLogRepositoryCustom {
    public WeavingWorkLogRepositoryImpl() {
        super(WeavingWorkLog.class);
    }

    @Lazy
    @Autowired
    public void setRepository(WeavingWorkLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public PageResult<WeavingLogResource> getAll(Long customerId, Long locationId, Integer orderNo, String receiptNumber, Date createdDateFrom, Date createdDateTo, Long designId, Long printId, Long sizeId, Pageable pageable) {
        QWeavingWorkLog weavingWorkLog = QWeavingWorkLog.weavingWorkLog;


        JPQLQuery query = getQuery(weavingWorkLog, getWhereCondition(weavingWorkLog, customerId, locationId, orderNo, receiptNumber, createdDateFrom, createdDateTo, designId, printId, sizeId));
        Long totalCount = query.count();

        List<WeavingLogResource> batchEntries = query.limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .list(new QWeavingLogResource(
                        weavingWorkLog.id,
                        weavingWorkLog.receiptNumber,
                        weavingWorkLog.orderNo,
                        weavingWorkLog.location.name,
                        weavingWorkLog.customer.name,
                        weavingWorkLog.design.name,
                        weavingWorkLog.print.name,
                        weavingWorkLog.extraField,
                        weavingWorkLog.size.name,
                        weavingWorkLog.quantity,
                        weavingWorkLog.remarks,
                        weavingWorkLog.created
                ));

        return new PageResult<>(totalCount, pageable.getPageSize(), pageable.getPageNumber(), batchEntries);
    }

    @Override
    public List<WeavingLogResource> getForReport(Long customerId, Long locationId, Integer orderNo, String receiptNumber, Date createdDateFrom, Date createdDateTo, Long designId, Long printId, Long sizeId) {
        QWeavingWorkLog weavingWorkLog = QWeavingWorkLog.weavingWorkLog;


        JPQLQuery query = getQuery(weavingWorkLog, getWhereCondition(weavingWorkLog, customerId, locationId, orderNo, receiptNumber, createdDateFrom, createdDateTo, designId, printId, sizeId));

        return query
                .list(new QWeavingLogResource(
                        weavingWorkLog.id,
                        weavingWorkLog.receiptNumber,
                        weavingWorkLog.orderNo,
                        weavingWorkLog.location.name,
                        weavingWorkLog.customer.name,
                        weavingWorkLog.design.name,
                        weavingWorkLog.print.name,
                        weavingWorkLog.extraField,
                        weavingWorkLog.size.name,
                        weavingWorkLog.quantity,
                        weavingWorkLog.remarks,
                        weavingWorkLog.created
                ));

    }

    private JPQLQuery getQuery(QWeavingWorkLog weavingWorkLog, BooleanBuilder whereCondition) {
        return from(weavingWorkLog)
                .where(whereCondition);
    }

    private BooleanBuilder getWhereCondition(QWeavingWorkLog weavingWorkLog,
                                             Long customerId,
                                             Long locationId,
                                             Integer orderNo,
                                             String receiptNumber,
                                             Date createdDateFrom,
                                             Date createdDateTo,
                                             Long designId,
                                             Long printId,
                                             Long sizeId) {
        BooleanBuilder where = new BooleanBuilder();
        if (customerId != null) {
            where.and(weavingWorkLog.customer.id.eq(customerId));
        }
        if (locationId != null) {
            where.and(weavingWorkLog.location.id.eq(locationId));
        }
        if (customerId != null) {
            where.and(weavingWorkLog.customer.id.eq(customerId));
        }
        if (orderNo != null) {
            where.and(weavingWorkLog.orderNo.eq(orderNo));
        }
        if (receiptNumber != null) {
            where.and(weavingWorkLog.receiptNumber.eq(receiptNumber));
        }
        if (designId != null) {
            where.and(weavingWorkLog.design.id.eq(designId));
        }
        if (printId != null) {
            where.and(weavingWorkLog.print.id.eq(printId));
        }
        if (sizeId != null) {
            where.and(weavingWorkLog.size.id.eq(sizeId));
        }
        if (createdDateFrom != null) {
            where.and(weavingWorkLog.created.goe(createdDateFrom));
        }

        if (createdDateTo != null) {
            where.and(weavingWorkLog.created.loe(DateUtils.addDays(createdDateTo, 1)));
        }

        return where;
    }
}
