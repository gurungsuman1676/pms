package com.pms.app.service;

import com.pms.app.domain.ShawlEntry;
import com.pms.app.schema.ShawlEntryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by arjun on 6/27/2017.
 */
public interface ShawlEntryService {
    void update(ShawlEntryDto shawlEntryDto);

    Page<ShawlEntry> getAll(Long locationId, Long sizeId, Long yarnId, Long customerId, Long colorId, Long shawlId, Date entryDateFrom, Date entryDateTo, Date exportDateFrom, Date exportDateTo, Pageable pageable);

    void generateReport(Long locationId, Long sizeId, Long yarnId, Long customerId, Long colorId, Long shawlId, Date entryDateFrom, Date entryDateTo, Date exportDateFrom, Date exportDateTo, HttpServletResponse httpServletResponse);
}
