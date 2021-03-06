package com.pms.app.repo.repoImpl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.expr.CaseBuilder;
import com.pms.app.domain.QShawlInventory;
import com.pms.app.domain.QShawlInventoryBatch;
import com.pms.app.domain.ShawlInventory;
import com.pms.app.repo.ShawlInventoryRepository;
import com.pms.app.schema.PageResult;
import com.pms.app.schema.QShawlInventoryBatchDetailResource;
import com.pms.app.schema.QShawlInventoryDto;
import com.pms.app.schema.QShawlInventoryResource;
import com.pms.app.schema.ShawlInventoryBatchDetailResource;
import com.pms.app.schema.ShawlInventoryDto;
import com.pms.app.schema.ShawlInventoryResource;
import com.pms.app.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

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

    @Override
    public ShawlInventory findForExportBySizeIdAndColorIdAndDesignId(Long sizeId, Long colorId, Long designId) {
        QShawlInventory shawlInventory = QShawlInventory.shawlInventory;

        BooleanBuilder where = getBooleanBuilder(sizeId, colorId, designId, shawlInventory);
        return from(shawlInventory)
                .where(where)
                .singleResult(shawlInventory);
    }

    @Override
    public List<ShawlInventoryResource> getAllResources(Long sizeId, Long colorId, Long designId) {
        QShawlInventory shawlInventory = QShawlInventory.shawlInventory;

        BooleanBuilder where = getBooleanBuilder(sizeId, colorId, designId, shawlInventory);
        return from(shawlInventory)
                .where(where)
                .list(new QShawlInventoryResource(shawlInventory.id,
                        shawlInventory.designs.name,
                        shawlInventory.color.name,
                        shawlInventory.sizes.name,
                        shawlInventory.count));
    }


    private BooleanBuilder getBooleanBuilder(Long sizeId, Long colorId, Long designId, QShawlInventory shawlInventory) {
        BooleanBuilder where = new BooleanBuilder();
        if (designId != null) {
            where.and(shawlInventory.designs.id.eq(designId));
        }
        if (sizeId != null) {
            where.and(shawlInventory.sizes.id.eq(sizeId));
        }
        if (colorId != null) {
            where.and(shawlInventory.color.id.eq(colorId));
        }
        return where;
    }

    @Override
    public Page<ShawlInventory> getAll(Long sizeId, Long colorId, Long designId, Pageable pageable) {
        QShawlInventory shawlInventory = QShawlInventory.shawlInventory;
        BooleanBuilder where = getBooleanBuilder(sizeId, colorId, designId, shawlInventory);
        return repository.findAll(where, pageable);
    }

    @Override
    public PageResult<ShawlInventoryBatchDetailResource> getBatchDetails(Long id, Date createdFrom,
                                                                         Date createdTo, String receiptNumber,
                                                                         Pageable pageable) {
        QShawlInventoryBatch shawlInventoryBatch = QShawlInventoryBatch.shawlInventoryBatch;


        JPQLQuery query = getQuery(shawlInventoryBatch, getWhereCondition(shawlInventoryBatch, id, createdFrom, createdTo, receiptNumber));
        Long totalCount = query.count();

        List<ShawlInventoryBatchDetailResource> batchEntries = query.limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .list(new QShawlInventoryBatchDetailResource(
                        shawlInventoryBatch.created,
                        shawlInventoryBatch.quantity,
                        shawlInventoryBatch.inventoryCount,
                        shawlInventoryBatch.isEntry,
                        shawlInventoryBatch.receiptNumber,
                        shawlInventoryBatch.id
                ));
        return new PageResult<>(totalCount, pageable.getPageSize(), pageable.getPageNumber(), batchEntries);
    }

    @Override
    public List<ShawlInventoryBatchDetailResource> getAllForReport(Long id, Date createdFrom, Date createdTo, String receiptNumber) {
        QShawlInventoryBatch shawlInventoryBatch = QShawlInventoryBatch.shawlInventoryBatch;


        JPQLQuery query = getQuery(shawlInventoryBatch, getWhereCondition(shawlInventoryBatch, id, createdFrom, createdTo, receiptNumber));

        return query
                .list(new QShawlInventoryBatchDetailResource(
                        shawlInventoryBatch.created,
                        shawlInventoryBatch.quantity,
                        shawlInventoryBatch.inventoryCount,
                        shawlInventoryBatch.isEntry,
                        shawlInventoryBatch.receiptNumber,
                        shawlInventoryBatch.id
                ));
    }

    @Override
    public ShawlInventoryDto findByBatchId(Long batchId) {
        QShawlInventoryBatch shawlInventoryBatch = QShawlInventoryBatch.shawlInventoryBatch;

        return from(shawlInventoryBatch)
                .where(shawlInventoryBatch.id.eq(batchId))
                .singleResult(new QShawlInventoryDto(
                        shawlInventoryBatch.quantity,
                        shawlInventoryBatch.inventory.sizes.id,
                        shawlInventoryBatch.inventory.designs.id,
                        shawlInventoryBatch.inventory.color.id,
                        new CaseBuilder().when(shawlInventoryBatch.isEntry.isTrue()).then("ORDER-IN")
                                .otherwise("ORDER-OUT"),
                        shawlInventoryBatch.receiptNumber
                ));
    }

    private BooleanBuilder getWhereCondition(QShawlInventoryBatch shawlInventoryBatch, Long id, Date createdFrom, Date createdTo, String receiptNumber) {
        BooleanBuilder where = new BooleanBuilder();
        where.and(shawlInventoryBatch.inventory.id.eq(id));

        if (createdFrom != null) {
            where.and(shawlInventoryBatch.created.goe(createdFrom));
        }

        if (createdTo != null) {
            where.and(shawlInventoryBatch.created.loe(DateUtils.addDays(createdTo, 1)));
        }

        if (receiptNumber != null) {
            where.and(shawlInventoryBatch.receiptNumber.eq(receiptNumber));
        }
        where.and(shawlInventoryBatch.deleted.isFalse());
        return where;
    }

    private JPQLQuery getQuery(QShawlInventoryBatch shawlInventoryBatch, BooleanBuilder booleanBuilder) {
        return from(shawlInventoryBatch)
                .innerJoin(shawlInventoryBatch.inventory)
                .where(booleanBuilder)
                .orderBy(shawlInventoryBatch.id.desc());
    }

}
