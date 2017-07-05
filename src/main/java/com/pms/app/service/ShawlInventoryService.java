package com.pms.app.service;

import com.pms.app.domain.ShawlInventory;
import com.pms.app.schema.ShawlInventoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by arjun on 6/27/2017.
 */
public interface ShawlInventoryService {
    void update(ShawlInventoryDto shawlEntryDto);

    Page<ShawlInventory> getAll(Long locationId, Long sizeId, Long designId, Pageable pageable);

    void generateReport(Long locationId, Long sizeId, Long designId, HttpServletResponse httpServletResponse);
}
