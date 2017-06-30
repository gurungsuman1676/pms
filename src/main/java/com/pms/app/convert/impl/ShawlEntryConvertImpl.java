package com.pms.app.convert.impl;

import com.pms.app.convert.ShawlEntryConvert;
import com.pms.app.domain.ShawlEntry;
import com.pms.app.schema.ShawlEntryResource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */

@Service
public class ShawlEntryConvertImpl implements ShawlEntryConvert {
    @Override
    public List<ShawlEntryResource> convert(List<ShawlEntry> shawlEntries) {
        return null;
    }
}
