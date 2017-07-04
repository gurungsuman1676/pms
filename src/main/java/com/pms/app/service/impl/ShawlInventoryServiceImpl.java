package com.pms.app.service.impl;

import com.pms.app.service.ShawlInventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by arjun on 6/27/2017.
 */

@Service
@Transactional
public class ShawlInventoryServiceImpl implements ShawlInventoryService {

//    private final ShawlInventoryRepositoryCustom shawlEntryRepository;
//    private final ShawlEntryBatchRepository shawlEntryBatchRepository;
//    private final ShawlExportBatchRepository shawlExportBatchRepository;
//    private final EntityManager entityManager;
//    private final LocationRepository locationRepository;
//    private final ShawlActivityRepository shawlActivityRepository;
//    private final ShawlReportService shawlReportService;
//
//    @Autowired
//    public ShawlInventoryServiceImpl(ShawlInventoryRepositoryCustom shawlEntryRepository, ShawlEntryBatchRepository shawlEntryBatchRepository, ShawlExportBatchRepository shawlExportBatchRepository, EntityManager entityManager, LocationRepository locationRepository, ShawlActivityRepository shawlActivityRepository, ShawlReportService shawlReportService) {
//        this.shawlEntryRepository = shawlEntryRepository;
//        this.shawlEntryBatchRepository = shawlEntryBatchRepository;
//        this.shawlExportBatchRepository = shawlExportBatchRepository;
//        this.entityManager = entityManager;
//        this.locationRepository = locationRepository;
//        this.shawlActivityRepository = shawlActivityRepository;
//        this.shawlReportService = shawlReportService;
//    }
//
//    @Override
//    public void update(ShawlInventoryDto shawlEntryDto) {
//        Locations locationByName = locationRepository.findOne(shawlEntryDto.getLocationId());
//        switch (locationByName.getName()) {
//            case "ORDER-IN":
//                addEntryBatch(shawlEntryDto);
//                break;
//            case "ORDER-OUT":
//                addExportBatch(shawlEntryDto);
//                break;
//            default:
//                updateEntry(shawlEntryDto, locationByName.getName().equals(LocationEnum.REJECTED.getName()));
//        }
//
//
//    }
//
//    private void updateEntry(ShawlInventoryDto shawlEntryDto, boolean isReject) {
//        Long count = shawlEntryRepository.findCountByShawlIdColorIdYarnIdCustomerIdSizeId(shawlEntryDto.getShawlId(),
//                shawlEntryDto.getColorId(), shawlEntryDto.getYarnId(), shawlEntryDto.getCustomerId(),
//                shawlEntryDto.getSizeId());
//
//        if (count < shawlEntryDto.getQuantity()) {
//            throw new RuntimeException("The number of available shawl for provided values is " + count);
//        }
//
//
//        List<ShawlInventory> shawlEntryList = shawlEntryRepository.findByShawlIdColorIdYarnIdCustomerIdSizeId(shawlEntryDto.getShawlId(),
//                shawlEntryDto.getColorId(), shawlEntryDto.getYarnId(), shawlEntryDto.getCustomerId(),
//                shawlEntryDto.getSizeId(), shawlEntryDto.getQuantity());
//
//        shawlEntryList.forEach(shawlEntry -> {
//            shawlEntry.setLocation(entityManager.getReference(Locations.class, shawlEntryDto.getLocationId()));
//            ShawlActivity activity = new ShawlActivity();
//            activity.setLocation(entityManager.getReference(Locations.class, shawlEntryDto.getLocationId()));
//            activity.setShawlEntry(shawlEntry);
//            activity.setUser(AuthUtil.getCurrentUser());
//            shawlActivityRepository.save(activity);
//            shawlEntry.setRejected(isReject);
//        });
//
//        shawlEntryRepository.save(shawlEntryList);
//
//
//    }
//
//    private void addExportBatch(ShawlInventoryDto shawlEntryDto) {
//        Long count = shawlEntryRepository.findCountForExportByShawlIdColorIdYarnIdCustomerIdSizeId(shawlEntryDto.getShawlId(),
//                shawlEntryDto.getColorId(), shawlEntryDto.getYarnId(), shawlEntryDto.getCustomerId(),
//                shawlEntryDto.getSizeId());
//
//        if (count < shawlEntryDto.getQuantity()) {
//            throw new RuntimeException("The number of shawl available is " + count);
//        }
//
//        ShawlExportBatch shawlExportBatch = new ShawlExportBatch();
//        shawlExportBatch.setQuantity(shawlEntryDto.getQuantity());
//        shawlExportBatch.setCustomer(entityManager.getReference(ShawlCustomer.class, shawlEntryDto.getCustomerId()));
//        final ShawlExportBatch exportedBatch = shawlExportBatchRepository.save(shawlExportBatch);
//
//        List<ShawlInventory> shawlEntryList = shawlEntryRepository.findForExportByShawlIdColorIdYarnIdCustomerIdSizeId(shawlEntryDto.getShawlId(),
//                shawlEntryDto.getColorId(), shawlEntryDto.getYarnId(), shawlEntryDto.getCustomerId(),
//                shawlEntryDto.getSizeId(), shawlEntryDto.getQuantity());
//
//        shawlEntryList.forEach(shawlEntry -> {
//            shawlEntry.setLocation(entityManager.getReference(Locations.class, shawlEntryDto.getLocationId()));
//            shawlEntry.setShawlExportBatch(entityManager.getReference(ShawlExportBatch.class, exportedBatch.getId()));
//            shawlEntry.setCompleted(true);
//        });
//        shawlEntryRepository.save(shawlEntryList);
//
//
//    }
//
//    private void addEntryBatch(ShawlInventoryDto shawlEntryDto) {
//        ShawlEntryBatch shawlEntryBatch = new ShawlEntryBatch();
//        shawlEntryBatch.setQuantity(shawlEntryDto.getQuantity());
//        shawlEntryBatch.setShawl(entityManager.getReference(Shawl.class, shawlEntryDto.getShawlId()));
//        shawlEntryBatch.setShawlColor(entityManager.getReference(ShawlColor.class, shawlEntryDto.getColorId()));
//        if (shawlEntryDto.getCustomerId() != null) {
//            shawlEntryBatch.setShawlCustomer(entityManager.getReference(ShawlCustomer.class, shawlEntryDto.getCustomerId()));
//        }
//        shawlEntryBatch.setShawlYarn(entityManager.getReference(ShawlYarn.class, shawlEntryDto.getYarnId()));
//        shawlEntryBatch.setShawlSize(entityManager.getReference(ShawlSize.class, shawlEntryDto.getSizeId()));
//        shawlEntryBatch = shawlEntryBatchRepository.save(shawlEntryBatch);
//        for (int i = 1; i <= shawlEntryDto.getQuantity(); i++) {
//            ShawlInventory shawlEntry = new ShawlInventory();
//            shawlEntry.setLocation(entityManager.getReference(Locations.class, shawlEntryDto.getLocationId()));
//            shawlEntry.setShawlEntryBatch(entityManager.getReference(ShawlEntryBatch.class, shawlEntryBatch.getId()));
//            shawlEntryRepository.save(shawlEntry);
//        }
//
//    }
//
//    @Override
//    public Page<ShawlInventory> getAll(Long locationId, Long sizeId, Long yarnId, Long customerId, Long colorId, Long shawlId, Date entryDateFrom, Date entryDateTo, Date exportDateFrom, Date exportDateTo, Pageable pageable) {
//        return shawlEntryRepository.getAll(locationId, sizeId, yarnId, customerId, colorId, shawlId, entryDateFrom, entryDateTo, exportDateFrom, exportDateTo, pageable);
//    }
//
//    @Override
//    public void generateReport(Long locationId, Long sizeId, Long yarnId, Long customerId, Long colorId, Long shawlId, Date entryDateFrom, Date entryDateTo, Date exportDateFrom, Date exportDateTo, HttpServletResponse httpServletResponse) {
//       shawlReportService.generateReport(locationId,sizeId,yarnId,customerId,colorId,shawlId,entryDateFrom,entryDateTo,exportDateFrom,exportDateTo,httpServletResponse);
//    }


}
