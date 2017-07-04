package com.pms.app.repo.repoImpl;

import com.pms.app.domain.ShawlInventory;
import com.pms.app.repo.ShawlInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

/**
 * Created by arjun on 6/28/2017.
 */
public class ShawlInventoryRepositoryImpl extends AbstractRepositoryImpl<ShawlInventory, ShawlInventoryRepository> implements com.pms.app.repo.repoCustom.ShawlInventoryRepositoryCustom {
    public ShawlInventoryRepositoryImpl() {
        super(ShawlInventory.class);
    }

    @Lazy
    @Autowired
    public void setRepository(ShawlInventoryRepository repository) {
        this.repository = repository;
    }
//
//    @Override
//    public Long findCountForExportByShawlIdColorIdYarnIdCustomerIdSizeId(Long shawlId, Long shawlColorId, Long shawlYarnId, Long shawlCustomerId, Long shawlSizeId) {
//
//        QShawlEntry shawlEntry = QShawlEntry.shawlEntry;
//        return from(shawlEntry)
//                .innerJoin(shawlEntry.shawlEntryBatch)
//                .innerJoin(shawlEntry.shawlEntryBatch.shawl)
//                .innerJoin(shawlEntry.shawlEntryBatch.shawlColor)
//                .innerJoin(shawlEntry.shawlEntryBatch.shawlSize)
//                .innerJoin(shawlEntry.shawlEntryBatch.shawlYarn)
//                .leftJoin(shawlEntry.shawlEntryBatch.shawlCustomer)
//                .where(shawlEntry.shawlEntryBatch.shawlSize.id.eq(shawlSizeId)
//                        .and(shawlEntry.shawlEntryBatch.shawlColor.id.eq(shawlColorId))
//                        .and(shawlEntry.shawlEntryBatch.shawl.id.eq(shawlId))
//                        .and(shawlEntry.shawlEntryBatch.shawlYarn.id.eq(shawlYarnId))
//                        .and(shawlEntry.rejected.isFalse())
//                        .and(shawlEntry.completed.isFalse())
//                        .and((shawlEntry.shawlEntryBatch.shawlCustomer.isNull().or(shawlEntry.shawlEntryBatch.shawlCustomer.id.eq(shawlCustomerId))))
//                )
//                .count();
//    }
//
//    @Override
//    public List<ShawlInventory> findForExportByShawlIdColorIdYarnIdCustomerIdSizeId(Long shawlId, Long shawlColorId, Long shawlYarnId, Long shawlCustomerId, Long shawlSizeId, int quantity) {
//        QShawlEntry shawlEntry = QShawlEntry.shawlEntry;
//        return from(shawlEntry)
//                .innerJoin(shawlEntry.shawlEntryBatch)
//                .innerJoin(shawlEntry.shawlEntryBatch.shawl)
//                .innerJoin(shawlEntry.shawlEntryBatch.shawlColor)
//                .innerJoin(shawlEntry.shawlEntryBatch.shawlSize)
//                .innerJoin(shawlEntry.shawlEntryBatch.shawlYarn)
//                .leftJoin(shawlEntry.shawlEntryBatch.shawlCustomer)
//                .where(shawlEntry.shawlEntryBatch.shawlSize.id.eq(shawlSizeId)
//                        .and(shawlEntry.shawlEntryBatch.shawlColor.id.eq(shawlColorId))
//                        .and(shawlEntry.shawlEntryBatch.shawl.id.eq(shawlId))
//                        .and(shawlEntry.rejected.isFalse())
//                        .and(shawlEntry.completed.isFalse())
//                        .and(shawlEntry.shawlEntryBatch.shawlYarn.id.eq(shawlYarnId))
//                        .and((shawlEntry.shawlEntryBatch.shawlCustomer.isNull().or(shawlEntry.shawlEntryBatch.shawlCustomer.id.eq(shawlCustomerId))))
//                )
//                .orderBy(shawlEntry.shawlEntryBatch.shawlCustomer.id.asc().nullsLast())
//                .limit(quantity)
//                .list(shawlEntry);
//    }
//
//    @Override
//    public Long findCountByShawlIdColorIdYarnIdCustomerIdSizeId(Long shawlId, Long shawlColorId, Long shawlYarnId, Long shawlCustomerId, Long shawlSizeId) {
//        QShawlEntry shawlEntry = QShawlEntry.shawlEntry;
//        BooleanBuilder where = new BooleanBuilder();
//        if (shawlCustomerId == null) {
//            where.and(shawlEntry.shawlEntryBatch.shawlCustomer.isNull());
//        } else {
//            where.and(shawlEntry.shawlEntryBatch.shawlCustomer.id.eq(shawlCustomerId));
//
//        }
//        return from(shawlEntry)
//                .innerJoin(shawlEntry.shawlEntryBatch)
//                .innerJoin(shawlEntry.shawlEntryBatch.shawl)
//                .innerJoin(shawlEntry.shawlEntryBatch.shawlColor)
//                .innerJoin(shawlEntry.shawlEntryBatch.shawlSize)
//                .innerJoin(shawlEntry.shawlEntryBatch.shawlYarn)
//                .leftJoin(shawlEntry.shawlEntryBatch.shawlCustomer)
//                .where(shawlEntry.shawlEntryBatch.shawlSize.id.eq(shawlSizeId)
//                        .and(shawlEntry.shawlEntryBatch.shawlColor.id.eq(shawlColorId))
//                        .and(shawlEntry.shawlEntryBatch.shawl.id.eq(shawlId))
//                        .and(shawlEntry.rejected.isFalse())
//                        .and(shawlEntry.completed.isFalse())
//                        .and(shawlEntry.shawlEntryBatch.shawlYarn.id.eq(shawlYarnId))
//                        .and(where)
//                )
//                .count();
//    }
//
//    @Override
//    public List<ShawlInventory> findByShawlIdColorIdYarnIdCustomerIdSizeId(Long shawlId, Long shawlColorId, Long shawlYarnId, Long shawlCustomerId, Long shawlSizeId, int quantity) {
//        QShawlEntry shawlEntry = QShawlEntry.shawlEntry;
//        BooleanBuilder where = new BooleanBuilder();
//        if (shawlCustomerId == null) {
//            where.and(shawlEntry.shawlEntryBatch.shawlCustomer.isNull());
//        } else {
//            where.and(shawlEntry.shawlEntryBatch.shawlCustomer.id.eq(shawlCustomerId));
//
//        }
//        return from(shawlEntry)
//                .innerJoin(shawlEntry.shawlEntryBatch)
//                .innerJoin(shawlEntry.shawlEntryBatch.shawl)
//                .innerJoin(shawlEntry.shawlEntryBatch.shawlColor)
//                .innerJoin(shawlEntry.shawlEntryBatch.shawlSize)
//                .innerJoin(shawlEntry.shawlEntryBatch.shawlYarn)
//                .leftJoin(shawlEntry.shawlEntryBatch.shawlCustomer)
//                .where(shawlEntry.shawlEntryBatch.shawlSize.id.eq(shawlSizeId)
//                        .and(shawlEntry.shawlEntryBatch.shawlColor.id.eq(shawlColorId))
//                        .and(shawlEntry.shawlEntryBatch.shawl.id.eq(shawlId))
//                        .and(shawlEntry.rejected.isFalse())
//                        .and(shawlEntry.completed.isFalse())
//                        .and(shawlEntry.shawlEntryBatch.shawlYarn.id.eq(shawlYarnId))
//                        .and(where)
//                )
//                .limit(quantity)
//                .list(shawlEntry);
//    }
//
//    @Override
//    public Page<ShawlInventory> getAll(Long locationId,
//                                   Long sizeId,
//                                   Long yarnId,
//                                   Long customerId,
//                                   Long colorId,
//                                   Long shawlId,
//                                   Date entryDateFrom,
//                                   Date entryDateTo,
//                                   Date exportDateFrom,
//                                   Date exportDateTo,
//                                   Pageable pageable) {
//
//        BooleanBuilder where = getBoolenBuilder(locationId, sizeId, yarnId, customerId, colorId, shawlId, entryDateFrom, entryDateTo, exportDateFrom, exportDateTo);
//        QShawlEntry shawlEntry = QShawlEntry.shawlEntry;
//
//        JPQLQuery query = getQuery(shawlEntry, where);
//        Long totalCount = query.count();
//
//        List<ShawlInventory> shawlEntries = query.limit(pageable.getPageSize())
//                .offset(pageable.getOffset())
//                .list(shawlEntry);
//        return new PageImpl(shawlEntries, pageable, totalCount);
//    }
//
//
//    @Override
//    public List<ShawlInventoryResource> getAllResources(Long locationId,
//                                                    Long sizeId,
//                                                    Long yarnId,
//                                                    Long customerId,
//                                                    Long colorId,
//                                                    Long shawlId,
//                                                    Date entryDateFrom,
//                                                    Date entryDateTo,
//                                                    Date exportDateFrom,
//                                                    Date exportDateTo) {
//
//        BooleanBuilder where = getBoolenBuilder(locationId, sizeId, yarnId, customerId, colorId, shawlId, entryDateFrom, entryDateTo, exportDateFrom, exportDateTo);
//        QShawlEntry shawlEntry = QShawlEntry.shawlEntry;
//
//        return getQuery(shawlEntry, where).list(
//                new QShawlEntryResource(
//                        shawlEntry.id,
//                        shawlEntry.shawlEntryBatch.shawl.name,
//                        shawlEntry.shawlEntryBatch.shawlCustomer.name,
//                        shawlEntry.shawlExportBatch.customer.name,
//                        shawlEntry.shawlEntryBatch.shawlYarn.name,
//                        shawlEntry.shawlEntryBatch.shawlColor.name,
//                        shawlEntry.shawlEntryBatch.shawlSize.name,
//                        shawlEntry.location.name,
//                        shawlEntry.shawlEntryBatch.created,
//                        shawlEntry.shawlExportBatch.created
//                )
//        );
//    }
//
//    private JPQLQuery getQuery(QShawlEntry shawlEntry, BooleanBuilder where) {
//        return from(shawlEntry)
//                .innerJoin(shawlEntry.shawlEntryBatch)
//                .innerJoin(shawlEntry.shawlEntryBatch.shawl)
//                .innerJoin(shawlEntry.shawlEntryBatch.shawlYarn)
//                .innerJoin(shawlEntry.shawlEntryBatch.shawlSize)
//                .innerJoin(shawlEntry.shawlEntryBatch.shawlColor)
//                .leftJoin(shawlEntry.shawlEntryBatch.shawlCustomer)
//                .leftJoin(shawlEntry.shawlExportBatch)
//                .leftJoin(shawlEntry.shawlExportBatch.customer)
//                .where(where);
//    }
//
//
//    private BooleanBuilder getBoolenBuilder(Long locationId,
//                                            Long sizeId,
//                                            Long yarnId,
//                                            Long customerId,
//                                            Long colorId,
//                                            Long shawlId,
//                                            Date entryDateFrom,
//                                            Date entryDateTo,
//                                            Date exportDateFrom,
//                                            Date exportDateTo) {
//        BooleanBuilder where = new BooleanBuilder();
//        QShawlEntry shawlEntry = QShawlEntry.shawlEntry;
//        if (locationId != null) {
//            where.and(shawlEntry.location.id.eq(locationId));
//        }
//        if (shawlId != null) {
//            where.and(shawlEntry.shawlEntryBatch.shawl.id.eq(shawlId));
//        }
//        if (sizeId != null) {
//            where.and(shawlEntry.shawlEntryBatch.shawlSize.id.eq(sizeId));
//        }
//        if (yarnId != null) {
//            where.and(shawlEntry.shawlEntryBatch.shawlYarn.id.eq(yarnId));
//        }
//        if (customerId != null) {
//            where.and(shawlEntry.shawlEntryBatch.shawlCustomer.id.eq(customerId))
//                    .or(shawlEntry.shawlExportBatch.customer.id.eq(customerId));
//        }
//        if (colorId != null) {
//            where.and(shawlEntry.shawlEntryBatch.shawlColor.id.eq(colorId));
//        }
//        if (entryDateFrom != null) {
//            where.and(shawlEntry.shawlEntryBatch.created.goe(entryDateFrom));
//        }
//        if (entryDateTo != null) {
//            where.and(shawlEntry.shawlEntryBatch.created.loe(DateUtils.addDays(entryDateTo, 1)));
//        }
//        if (exportDateFrom != null) {
//            where.and(shawlEntry.shawlExportBatch.created.goe(exportDateFrom));
//        }
//        if (exportDateTo != null) {
//            where.and(shawlEntry.shawlExportBatch.created.loe(DateUtils.addDays(exportDateTo, 1)));
//        }
//        return where;
//
//    }
}
