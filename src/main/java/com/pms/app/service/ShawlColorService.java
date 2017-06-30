package com.pms.app.service;

import com.pms.app.domain.ShawlColor;
import com.pms.app.schema.ShawlColorDto;

import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */
public interface ShawlColorService {
    List<ShawlColor> getAll();

    ShawlColor add(ShawlColorDto shawlColorDto);

    ShawlColor get(Long id);

    ShawlColor edit(Long id, ShawlColorDto shawlColorDto);
}
