package com.pms.app.convert.impl;

import com.pms.app.convert.DesignConvert;
import com.pms.app.convert.ShawlColorConvert;
import com.pms.app.convert.ShawlInventoryConvert;
import com.pms.app.convert.SizeConvert;
import com.pms.app.domain.ShawlInventory;
import com.pms.app.schema.ShawlInventoryResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by arjun on 6/27/2017.
 */

@Service
public class ShawlInventoryConvertImpl implements ShawlInventoryConvert {

    private final DesignConvert designConvert;
    private final SizeConvert sizeConvert;
    private final ShawlColorConvert colorConvert;

    @Autowired
    public ShawlInventoryConvertImpl(DesignConvert designConvert, SizeConvert sizeConvert, ShawlColorConvert colorConvert) {
        this.designConvert = designConvert;
        this.sizeConvert = sizeConvert;
        this.colorConvert = colorConvert;
    }


    @Override
    public List<ShawlInventoryResource> convert(List<ShawlInventory> shawlEntries) {
        return shawlEntries.stream().map(this::convert).collect(Collectors.toList());
    }

    private ShawlInventoryResource convert(ShawlInventory shawlEntry) {
        return ShawlInventoryResource.builder()
                .id(shawlEntry.getId())
                .color(colorConvert.convert(shawlEntry.getColor()))
                .size(sizeConvert.convertSize(shawlEntry.getSizes()))
                .design(designConvert.convert(shawlEntry.getDesigns()))
                .count(shawlEntry.getCount())
                .build();
    }
}
