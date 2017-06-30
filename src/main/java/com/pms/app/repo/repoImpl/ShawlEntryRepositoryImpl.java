package com.pms.app.repo.repoImpl;

import com.mysema.query.BooleanBuilder;
import com.pms.app.domain.QShawlEntry;
import com.pms.app.domain.ShawlEntry;
import com.pms.app.repo.ShawlEntryRepository;
import com.pms.app.repo.repoCustom.ShawlEntryRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

/**
 * Created by arjun on 6/28/2017.
 */
public class ShawlEntryRepositoryImpl extends AbstractRepositoryImpl<ShawlEntry, ShawlEntryRepository> implements ShawlEntryRepositoryCustom {
    public ShawlEntryRepositoryImpl() {
        super(ShawlEntry.class);
    }

    @Lazy
    @Autowired
    public void setRepository(ShawlEntryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long findCountForExportByShawlIdColorIdYarnIdCustomerIdSizeId(Long shawlId, Long shawlColorId, Long shawlYarnId, Long shawlCustomerId, Long shawlSizeId) {

        QShawlEntry shawlEntry = QShawlEntry.shawlEntry;
        return from(shawlEntry)
                .innerJoin(shawlEntry.shawlEntryBatch)
                .innerJoin(shawlEntry.shawlEntryBatch.shawl)
                .innerJoin(shawlEntry.shawlEntryBatch.shawlColor)
                .innerJoin(shawlEntry.shawlEntryBatch.shawlSize)
                .innerJoin(shawlEntry.shawlEntryBatch.shawlYarn)
                .leftJoin(shawlEntry.shawlEntryBatch.shawlCustomer)
                .where(shawlEntry.shawlEntryBatch.shawlSize.id.eq(shawlSizeId)
                        .and(shawlEntry.shawlEntryBatch.shawlColor.id.eq(shawlColorId))
                        .and(shawlEntry.shawlEntryBatch.shawl.id.eq(shawlId))
                        .and(shawlEntry.shawlEntryBatch.shawlYarn.id.eq(shawlYarnId))
                        .and((shawlEntry.shawlEntryBatch.shawlCustomer.isNull().or(shawlEntry.shawlEntryBatch.shawlCustomer.id.eq(shawlCustomerId))))
                )
                .count();
    }

    @Override
    public List<ShawlEntry> findForExportByShawlIdColorIdYarnIdCustomerIdSizeId(Long shawlId, Long shawlColorId, Long shawlYarnId, Long shawlCustomerId, Long shawlSizeId, int quantity) {
        QShawlEntry shawlEntry = QShawlEntry.shawlEntry;
        return from(shawlEntry)
                .innerJoin(shawlEntry.shawlEntryBatch)
                .innerJoin(shawlEntry.shawlEntryBatch.shawl)
                .innerJoin(shawlEntry.shawlEntryBatch.shawlColor)
                .innerJoin(shawlEntry.shawlEntryBatch.shawlSize)
                .innerJoin(shawlEntry.shawlEntryBatch.shawlYarn)
                .leftJoin(shawlEntry.shawlEntryBatch.shawlCustomer)
                .where(shawlEntry.shawlEntryBatch.shawlSize.id.eq(shawlSizeId)
                        .and(shawlEntry.shawlEntryBatch.shawlColor.id.eq(shawlColorId))
                        .and(shawlEntry.shawlEntryBatch.shawl.id.eq(shawlId))
                        .and(shawlEntry.shawlEntryBatch.shawlYarn.id.eq(shawlYarnId))
                        .and((shawlEntry.shawlEntryBatch.shawlCustomer.isNull().or(shawlEntry.shawlEntryBatch.shawlCustomer.id.eq(shawlCustomerId))))
                )
                .limit(quantity)
                .list(shawlEntry);
    }

    @Override
    public Long findCountByShawlIdColorIdYarnIdCustomerIdSizeId(Long shawlId, Long shawlColorId, Long shawlYarnId, Long shawlCustomerId, Long shawlSizeId) {
        QShawlEntry shawlEntry = QShawlEntry.shawlEntry;
        BooleanBuilder where = new BooleanBuilder();
        if (shawlCustomerId == null) {
            where.and(shawlEntry.shawlEntryBatch.shawlCustomer.isNull());
        } else {
            where.and(shawlEntry.shawlEntryBatch.shawlCustomer.id.eq(shawlCustomerId));

        }
        return from(shawlEntry)
                .innerJoin(shawlEntry.shawlEntryBatch)
                .innerJoin(shawlEntry.shawlEntryBatch.shawl)
                .innerJoin(shawlEntry.shawlEntryBatch.shawlColor)
                .innerJoin(shawlEntry.shawlEntryBatch.shawlSize)
                .innerJoin(shawlEntry.shawlEntryBatch.shawlYarn)
                .leftJoin(shawlEntry.shawlEntryBatch.shawlCustomer)
                .where(shawlEntry.shawlEntryBatch.shawlSize.id.eq(shawlSizeId)
                        .and(shawlEntry.shawlEntryBatch.shawlColor.id.eq(shawlColorId))
                        .and(shawlEntry.shawlEntryBatch.shawl.id.eq(shawlId))
                        .and(shawlEntry.shawlEntryBatch.shawlYarn.id.eq(shawlYarnId))
                        .and(where)
                )
                .count();
    }

    @Override
    public List<ShawlEntry> findByShawlIdColorIdYarnIdCustomerIdSizeId(Long shawlId, Long shawlColorId, Long shawlYarnId, Long shawlCustomerId, Long shawlSizeId, int quantity) {
        QShawlEntry shawlEntry = QShawlEntry.shawlEntry;
        BooleanBuilder where = new BooleanBuilder();
        if (shawlCustomerId == null) {
            where.and(shawlEntry.shawlEntryBatch.shawlCustomer.isNull());
        } else {
            where.and(shawlEntry.shawlEntryBatch.shawlCustomer.id.eq(shawlCustomerId));

        }
        return from(shawlEntry)
                .innerJoin(shawlEntry.shawlEntryBatch)
                .innerJoin(shawlEntry.shawlEntryBatch.shawl)
                .innerJoin(shawlEntry.shawlEntryBatch.shawlColor)
                .innerJoin(shawlEntry.shawlEntryBatch.shawlSize)
                .innerJoin(shawlEntry.shawlEntryBatch.shawlYarn)
                .leftJoin(shawlEntry.shawlEntryBatch.shawlCustomer)
                .where(shawlEntry.shawlEntryBatch.shawlSize.id.eq(shawlSizeId)
                        .and(shawlEntry.shawlEntryBatch.shawlColor.id.eq(shawlColorId))
                        .and(shawlEntry.shawlEntryBatch.shawl.id.eq(shawlId))
                        .and(shawlEntry.shawlEntryBatch.shawlYarn.id.eq(shawlYarnId))
                        .and(where)
                )
                .limit(quantity)
                .list(shawlEntry);
    }
}
