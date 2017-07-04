package com.pms.app.convert.impl;

import com.pms.app.convert.ShawlInventoryConvert;
import org.springframework.stereotype.Service;

/**
 * Created by arjun on 6/27/2017.
 */

@Service
public class ShawlInventoryConvertImpl implements ShawlInventoryConvert {

//    private final ShawlCustomerConvert customerConvert;
//    private final ShawlConvert shawlConvert;
//    private final ShawlYarnConvert yarnConvert;
//    private final ShawlSizeConvert sizeConvert;
//    private final ShawlColorConvert colorConvert;
//    private final LocationConvert locationConvert;
//
//
//    @Autowired
//    public ShawlInventoryConvertImpl(ShawlCustomerConvert customerConvert,
//                                 ShawlConvert shawlConvert,
//                                 ShawlYarnConvert yarnConvert,
//                                 ShawlSizeConvert sizeConvert,
//                                 ShawlColorConvert colorConvert,
//                                 LocationConvert locationConvert) {
//        this.customerConvert = customerConvert;
//        this.shawlConvert = shawlConvert;
//        this.yarnConvert = yarnConvert;
//        this.sizeConvert = sizeConvert;
//        this.colorConvert = colorConvert;
//        this.locationConvert = locationConvert;
//    }
//
//    @Override
//    public List<ShawlInventoryResource> convert(List<ShawlInventory> shawlEntries) {
//        return shawlEntries.stream().map(this::convert).collect(Collectors.toList());
//    }
//
//    private ShawlInventoryResource convert(ShawlInventory shawlEntry) {
//        return ShawlInventoryResource.builder()
//                .id(shawlEntry.getId())
//                .color(colorConvert.convert(shawlEntry.getShawlEntryBatch().getShawlColor()))
//                .size(sizeConvert.convert(shawlEntry.getShawlEntryBatch().getShawlSize()))
//                .yarn(yarnConvert.convert(shawlEntry.getShawlEntryBatch().getShawlYarn()))
//                .shawl(shawlConvert.convert(shawlEntry.getShawlEntryBatch().getShawl()))
//                .importCustomer(customerConvert.convert(shawlEntry.getShawlEntryBatch().getShawlCustomer()))
//                .exportCustomer(shawlEntry.getShawlExportBatch() == null ? null : customerConvert.convert(shawlEntry.getShawlExportBatch().getCustomer()))
//                .importDate(shawlEntry.getShawlEntryBatch().getCreated())
//                .location(locationConvert.convert(shawlEntry.getLocation()))
//                .exportDate(shawlEntry.getShawlExportBatch() != null ? shawlEntry.getShawlExportBatch().getCreated() : null)
//                .build();
//    }
}
