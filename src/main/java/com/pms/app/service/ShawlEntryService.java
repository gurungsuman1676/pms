package com.pms.app.service;

import com.pms.app.domain.ShawlEntry;
import com.pms.app.schema.ShawlEntryDto;
import org.springframework.data.domain.Page;

/**
 * Created by arjun on 6/27/2017.
 */
public interface ShawlEntryService {
    void update(ShawlEntryDto shawlEntryDto);

    Page<ShawlEntry> getAll();
}
