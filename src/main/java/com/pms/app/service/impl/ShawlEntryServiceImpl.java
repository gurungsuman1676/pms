package com.pms.app.service.impl;

import com.pms.app.domain.LocationEnum;
import com.pms.app.domain.Locations;
import com.pms.app.domain.Shawl;
import com.pms.app.domain.ShawlColor;
import com.pms.app.domain.ShawlCustomer;
import com.pms.app.domain.ShawlEntry;
import com.pms.app.domain.ShawlEntryBatch;
import com.pms.app.domain.ShawlExportBatch;
import com.pms.app.domain.ShawlSize;
import com.pms.app.domain.ShawlYarn;
import com.pms.app.repo.LocationRepository;
import com.pms.app.repo.ShawlEntryBatchRepository;
import com.pms.app.repo.ShawlEntryRepository;
import com.pms.app.repo.ShawlExportBatchRepository;
import com.pms.app.schema.ShawlEntryDto;
import com.pms.app.service.ShawlEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.print.attribute.standard.ReferenceUriSchemesSupported;
import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */

@Component
public class ShawlEntryServiceImpl implements ShawlEntryService {

    private final ShawlEntryRepository shawlEntryRepository;
    private final ShawlEntryBatchRepository shawlEntryBatchRepository;
    private final ShawlExportBatchRepository shawlExportBatchRepository;
    private final EntityManager entityManager;
    private final LocationRepository locationRepository;

    @Autowired
    public ShawlEntryServiceImpl(ShawlEntryRepository shawlEntryRepository, ShawlEntryBatchRepository shawlEntryBatchRepository, ShawlExportBatchRepository shawlExportBatchRepository, EntityManager entityManager, LocationRepository locationRepository) {
        this.shawlEntryRepository = shawlEntryRepository;
        this.shawlEntryBatchRepository = shawlEntryBatchRepository;
        this.shawlExportBatchRepository = shawlExportBatchRepository;
        this.entityManager = entityManager;
        this.locationRepository = locationRepository;
    }

    @Override
    public void update(ShawlEntryDto shawlEntryDto) {
        Locations locationByName = locationRepository.findByName(LocationEnum.ORDER_IN.getName());
        switch (locationByName.getName()) {
            case "ORDER-IN":
                addEntryBatch(shawlEntryDto);
                break;
            case "ORDER-OUT":
                addExportBatch(shawlEntryDto);
                break;
            default:
                updateEntry(shawlEntryDto);
        }


    }

    private void updateEntry(ShawlEntryDto shawlEntryDto) {
        Long count = shawlEntryRepository.findCountByShawlIdColorIdYarnIdCustomerIdSizeId(shawlEntryDto.getShawlId(),
                shawlEntryDto.getShawlColorId(), shawlEntryDto.getShawlYarnId(), shawlEntryDto.getShawlCustomerId(),
                shawlEntryDto.getShawlSizeId());

        if (count < shawlEntryDto.getQuantity()) {
            throw new RuntimeException("The number of shawl available is " + count);
        }


        List<ShawlEntry> shawlEntryList = shawlEntryRepository.findByShawlIdColorIdYarnIdCustomerIdSizeId(shawlEntryDto.getShawlId(),
                shawlEntryDto.getShawlColorId(), shawlEntryDto.getShawlYarnId(), shawlEntryDto.getShawlCustomerId(),
                shawlEntryDto.getShawlSizeId(), shawlEntryDto.getQuantity());

        shawlEntryList.forEach(shawlEntry -> {
            shawlEntry.setLocations(entityManager.getReference(Locations.class, shawlEntryDto.getLocationId()));
        });

        shawlEntryRepository.save(shawlEntryList);


    }

    private void addExportBatch(ShawlEntryDto shawlEntryDto) {
        Long count = shawlEntryRepository.findCountForExportByShawlIdColorIdYarnIdCustomerIdSizeId(shawlEntryDto.getShawlId(),
                shawlEntryDto.getShawlColorId(), shawlEntryDto.getShawlYarnId(), shawlEntryDto.getShawlCustomerId(),
                shawlEntryDto.getShawlSizeId());

        if (count < shawlEntryDto.getQuantity()) {
            throw new RuntimeException("The number of shawl available is " + count);
        }

        ShawlExportBatch shawlExportBatch = new ShawlExportBatch();
        shawlExportBatch.setQuantity(shawlEntryDto.getQuantity());
        shawlExportBatch.setCustomer(entityManager.getReference(ShawlCustomer.class, shawlEntryDto.getShawlCustomerId()));
        final ShawlExportBatch exportedBatch = shawlExportBatchRepository.save(shawlExportBatch);

        List<ShawlEntry> shawlEntryList = shawlEntryRepository.findForExportByShawlIdColorIdYarnIdCustomerIdSizeId(shawlEntryDto.getShawlId(),
                shawlEntryDto.getShawlColorId(), shawlEntryDto.getShawlYarnId(), shawlEntryDto.getShawlCustomerId(),
                shawlEntryDto.getShawlSizeId(), shawlEntryDto.getQuantity());

        shawlEntryList.forEach(shawlEntry -> {
            shawlEntry.setLocations(entityManager.getReference(Locations.class, shawlEntryDto.getLocationId()));
            shawlEntry.setShawlExportBatch(entityManager.getReference(ShawlExportBatch.class, exportedBatch.getId()));
        });
        shawlEntryRepository.save(shawlEntryList);


    }

    private void addEntryBatch(ShawlEntryDto shawlEntryDto) {
        ShawlEntryBatch shawlEntryBatch = new ShawlEntryBatch();
        shawlEntryBatch.setQuantity(shawlEntryDto.getQuantity());
        shawlEntryBatch.setShawl(entityManager.getReference(Shawl.class, shawlEntryDto.getShawlId()));
        shawlEntryBatch.setShawlColor(entityManager.getReference(ShawlColor.class, shawlEntryDto.getShawlColorId()));
        if (shawlEntryDto.getShawlCustomerId() != null) {
            shawlEntryBatch.setShawlCustomer(entityManager.getReference(ShawlCustomer.class, shawlEntryDto.getShawlCustomerId()));
        }
        shawlEntryBatch.setShawlYarn(entityManager.getReference(ShawlYarn.class, shawlEntryDto.getShawlYarnId()));
        shawlEntryBatch.setShawlSize(entityManager.getReference(ShawlSize.class, shawlEntryDto.getShawlSizeId()));
        shawlEntryBatch = shawlEntryBatchRepository.save(shawlEntryBatch);
        for (int i = 1; i == shawlEntryDto.getQuantity(); i++) {
            ShawlEntry shawlEntry = new ShawlEntry();
            shawlEntry.setLocations(entityManager.getReference(Locations.class, shawlEntryDto.getLocationId()));
            shawlEntry.setShawlEntryBatch(entityManager.getReference(ShawlEntryBatch.class, shawlEntryBatch.getId()));
            shawlEntryRepository.save(shawlEntry);
        }

    }

    @Override
    public Page<ShawlEntry> getAll() {
        return null;
    }
}
