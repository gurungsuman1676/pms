package com.pms.app.repo.repoImpl;

import com.mysema.query.BooleanBuilder;
import com.pms.app.domain.QShawlInventory;
import com.pms.app.domain.ShawlInventory;
import com.pms.app.repo.ShawlInventoryRepository;
import com.pms.app.schema.QShawlInventoryResource;
import com.pms.app.schema.ShawlInventoryResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    public ShawlInventory findCountForExportBySizeIdAndColorIdAndDesignId(Long sizeId, Long colorId, Long designId) {
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
}
