package com.pms.app.convert.impl;

import com.pms.app.convert.LocationConvert;
import com.pms.app.convert.ShawlColorConvert;
import com.pms.app.convert.ShawlConvert;
import com.pms.app.convert.ShawlCustomerConvert;
import com.pms.app.convert.ShawlEntryConvert;
import com.pms.app.convert.ShawlSizeConvert;
import com.pms.app.convert.ShawlYarnConvert;
import com.pms.app.domain.ShawlEntry;
import com.pms.app.schema.ShawlEntryResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by arjun on 6/27/2017.
 */

@Service
public class ShawlEntryConvertImpl implements ShawlEntryConvert {

    private final ShawlCustomerConvert customerConvert;
    private final ShawlConvert shawlConvert;
    private final ShawlYarnConvert yarnConvert;
    private final ShawlSizeConvert sizeConvert;
    private final ShawlColorConvert colorConvert;
    private final LocationConvert locationConvert;


    @Autowired
    public ShawlEntryConvertImpl(ShawlCustomerConvert customerConvert,
                                 ShawlConvert shawlConvert,
                                 ShawlYarnConvert yarnConvert,
                                 ShawlSizeConvert sizeConvert,
                                 ShawlColorConvert colorConvert,
                                 LocationConvert locationConvert) {
        this.customerConvert = customerConvert;
        this.shawlConvert = shawlConvert;
        this.yarnConvert = yarnConvert;
        this.sizeConvert = sizeConvert;
        this.colorConvert = colorConvert;
        this.locationConvert = locationConvert;
    }

    @Override
    public List<ShawlEntryResource> convert(List<ShawlEntry> shawlEntries) {
        return shawlEntries.stream().map(this::convert).collect(Collectors.toList());
    }

    private ShawlEntryResource convert(ShawlEntry shawlEntry) {
        return ShawlEntryResource.builder()
                .id(shawlEntry.getId())
                .color(colorConvert.convert(shawlEntry.getShawlEntryBatch().getShawlColor()))
                .size(sizeConvert.convert(shawlEntry.getShawlEntryBatch().getShawlSize()))
                .yarn(yarnConvert.convert(shawlEntry.getShawlEntryBatch().getShawlYarn()))
                .shawl(shawlConvert.convert(shawlEntry.getShawlEntryBatch().getShawl()))
                .importCustomer(customerConvert.convert(shawlEntry.getShawlEntryBatch().getShawlCustomer()))
                .exportCustomer(shawlEntry.getShawlExportBatch() == null ? null : customerConvert.convert(shawlEntry.getShawlExportBatch().getCustomer()))
                .importDate(shawlEntry.getShawlEntryBatch().getCreated())
                .location(locationConvert.convert(shawlEntry.getLocation()))
                .exportDate(shawlEntry.getShawlExportBatch() != null ? shawlEntry.getShawlExportBatch().getCreated() : null)
                .build();
    }
}
