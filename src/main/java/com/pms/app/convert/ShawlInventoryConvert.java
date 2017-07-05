package com.pms.app.convert;

import com.pms.app.domain.ShawlInventory;
import com.pms.app.schema.ShawlInventoryResource;

import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */
public interface ShawlInventoryConvert {
    List<ShawlInventoryResource> convert(List<ShawlInventory> shawlEntries);
}
