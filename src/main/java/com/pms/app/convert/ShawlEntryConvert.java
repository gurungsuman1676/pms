package com.pms.app.convert;

import com.pms.app.domain.ShawlEntry;
import com.pms.app.schema.ShawlEntryResource;

import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */
public interface ShawlEntryConvert {
    List<ShawlEntryResource> convert(List<ShawlEntry> shawlEntries);
}
