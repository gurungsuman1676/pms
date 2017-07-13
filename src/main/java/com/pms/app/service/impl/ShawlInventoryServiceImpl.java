package com.pms.app.service.impl;

import com.pms.app.domain.Designs;
import com.pms.app.domain.ShawlInventoryBatch;
import com.pms.app.domain.ShawlColor;
import com.pms.app.domain.ShawlInventory;
import com.pms.app.domain.Sizes;
import com.pms.app.repo.ShawlInventoryBatchRepository;
import com.pms.app.repo.ShawlInventoryRepository;
import com.pms.app.schema.PageResult;
import com.pms.app.schema.ShawlInventoryBatchDetailResource;
import com.pms.app.schema.ShawlInventoryDto;
import com.pms.app.service.ShawlInventoryService;
import com.pms.app.service.ShawlReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by arjun on 6/27/2017.
 */

@Service
@Transactional
public class ShawlInventoryServiceImpl implements ShawlInventoryService {

    private final ShawlInventoryRepository shawlInventoryRepository;
    private final ShawlInventoryBatchRepository shawlInventoryBatchRepository;
    private final EntityManager entityManager;
    private final ShawlReportService shawlReportService;

    @Autowired
    public ShawlInventoryServiceImpl(ShawlInventoryRepository shawlInventoryRepository,
                                     ShawlInventoryBatchRepository shawlInventoryBatchRepository,
                                     EntityManager entityManager,
                                     ShawlReportService shawlReportService) {
        this.shawlInventoryRepository = shawlInventoryRepository;
        this.shawlInventoryBatchRepository = shawlInventoryBatchRepository;
        this.entityManager = entityManager;
        this.shawlReportService = shawlReportService;
    }

    @Override
    public void update(ShawlInventoryDto shawlEntryDto) {
        switch (shawlEntryDto.getLocation()) {
            case "ORDER-IN":
                addEntryBatch(shawlEntryDto);
                break;
            case "ORDER-OUT":
                addExportBatch(shawlEntryDto);
                break;
            default:
                throw new RuntimeException("Invalid location entered");
        }


    }


    private void addExportBatch(ShawlInventoryDto shawlEntryDto) {
        ShawlInventory inventory = shawlInventoryRepository.findForExportBySizeIdAndColorIdAndDesignId(shawlEntryDto.getSizeId(),
                shawlEntryDto.getColorId(),
                shawlEntryDto.getDesignId());

        if (inventory == null) {
            throw new RuntimeException("No shawl with provided details is in inventory");
        }
        if (inventory.getCount() < shawlEntryDto.getQuantity()) {
            throw new RuntimeException("Available shawls for provided values is " + inventory.getCount());
        }

        inventory.setCount(inventory.getCount() - shawlEntryDto.getQuantity());
        inventory = shawlInventoryRepository.save(inventory);
        ShawlInventoryBatch shawlInventoryBatch = new ShawlInventoryBatch();
        shawlInventoryBatch.setInventory(inventory);
        shawlInventoryBatch.setQuantity(shawlEntryDto.getQuantity());
        shawlInventoryBatch.setInventoryCount(inventory.getCount());
        shawlInventoryBatch.setEntry(false);
        shawlInventoryBatch.setReceiptNumber(shawlEntryDto.getReceiptNumber());
        shawlInventoryBatchRepository.save(shawlInventoryBatch);


    }

    private void addEntryBatch(ShawlInventoryDto shawlEntryDto) {

        ShawlInventory inventory = shawlInventoryRepository.findForExportBySizeIdAndColorIdAndDesignId(shawlEntryDto.getSizeId(),
                shawlEntryDto.getColorId(),
                shawlEntryDto.getDesignId());

        if (inventory == null) {
            inventory = new ShawlInventory();
            inventory.setColor(entityManager.getReference(ShawlColor.class, shawlEntryDto.getColorId()));
            inventory.setDesigns(entityManager.getReference(Designs.class, shawlEntryDto.getDesignId()));
            inventory.setSizes(entityManager.getReference(Sizes.class, shawlEntryDto.getSizeId()));
            inventory.setCount(0);
        }
        inventory.setCount(inventory.getCount() + shawlEntryDto.getQuantity());
        inventory = shawlInventoryRepository.save(inventory);
        ShawlInventoryBatch shawlInventoryBatch = new ShawlInventoryBatch();
        shawlInventoryBatch.setInventory(inventory);
        shawlInventoryBatch.setQuantity(shawlEntryDto.getQuantity());
        shawlInventoryBatch.setInventoryCount(inventory.getCount());
        shawlInventoryBatch.setEntry(true);
        shawlInventoryBatch.setReceiptNumber(shawlEntryDto.getReceiptNumber());
        shawlInventoryBatchRepository.save(shawlInventoryBatch);
    }

    @Override
    public Page<ShawlInventory> getAll(Long sizeId, Long colorId, Long designId, Pageable pageable) {
        return shawlInventoryRepository.getAll(sizeId, colorId, designId, pageable);
    }

    @Override
    public void generateReport(Long sizeId, Long colorId, Long designId, HttpServletResponse httpServletResponse) {
        shawlReportService.generateReport(sizeId, colorId, designId, httpServletResponse);
    }

    @Override
    public PageResult<ShawlInventoryBatchDetailResource> getBatchDetails(Long id,
                                                                         Date createdFrom, Date createdTo,
                                                                         String receiptNumber, Pageable pageable) {
        return shawlInventoryRepository.getBatchDetails(id, createdFrom, createdTo, receiptNumber, pageable);
    }

    @Override
    public void getBatchDetailsReport(Long id, Date createdFrom, Date createdTo,
                                      String receiptNumber, HttpServletResponse httpServletResponse) {
        shawlReportService.getBatchDetailReport(id, createdFrom, createdTo, receiptNumber, httpServletResponse);
    }

    @Override
    public ShawlInventoryDto get(Long batchId) {
        return shawlInventoryRepository.findByBatchId(batchId);
    }

    @Override
    public void delete(Long batchId) {
        ShawlInventoryBatch batch = shawlInventoryBatchRepository.findOne(batchId);
        ShawlInventory inventory = batch.getInventory();
        if (batch.isEntry()) {
            inventory.setCount(inventory.getCount() - batch.getQuantity());
        } else {
            inventory.setCount(inventory.getCount() + batch.getQuantity());
        }
        shawlInventoryRepository.save(inventory);
        batch.setDeleted(true);
        shawlInventoryBatchRepository.save(batch);
    }

    @Override
    public void edit(Long batchId, ShawlInventoryDto shawlInventoryDto) {
        ShawlInventoryBatch shawlInventoryBatch = shawlInventoryBatchRepository.findOne(batchId);
        ShawlInventory inventory = shawlInventoryBatch.getInventory();

        if (shawlInventoryBatch.isEntry()) {
            inventory.setCount(inventory.getCount() - shawlInventoryBatch.getQuantity());
        } else {
            inventory.setCount(inventory.getCount() + shawlInventoryBatch.getQuantity());
        }

        shawlInventoryRepository.save(inventory);

        ShawlInventory newInventory = shawlInventoryRepository.findForExportBySizeIdAndColorIdAndDesignId(shawlInventoryDto.getSizeId(),
                shawlInventoryDto.getColorId(),
                shawlInventoryDto.getDesignId());

        if (newInventory == null) {
            newInventory = new ShawlInventory();
            newInventory.setCount(0);
            newInventory.setColor(entityManager.getReference(ShawlColor.class, shawlInventoryDto.getColorId()));
            newInventory.setDesigns(entityManager.getReference(Designs.class, shawlInventoryDto.getDesignId()));
            newInventory.setSizes(entityManager.getReference(Sizes.class, shawlInventoryDto.getSizeId()));
        }
        newInventory = shawlInventoryRepository.save(newInventory);
        if (shawlInventoryDto.getLocation().equalsIgnoreCase("ORDER-IN")) {
            newInventory.setCount(newInventory.getCount() + shawlInventoryDto.getQuantity());
        } else {
            if (shawlInventoryDto.getQuantity() < newInventory.getCount()) {
                throw new RuntimeException("Available shawls for provided values on update is " + newInventory.getCount());
            }
            newInventory.setCount(newInventory.getCount() - shawlInventoryDto.getQuantity());
        }
        newInventory = shawlInventoryRepository.save(newInventory);
        shawlInventoryBatch.setInventory(newInventory);
        shawlInventoryBatch.setReceiptNumber(shawlInventoryDto.getReceiptNumber());
        shawlInventoryBatch.setQuantity(shawlInventoryDto.getQuantity());
        shawlInventoryBatch.setInventoryCount(inventory.getCount());
        shawlInventoryBatchRepository.save(shawlInventoryBatch);
    }


}
