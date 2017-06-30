package com.pms.app.service;

import com.pms.app.domain.ShawlSize;
import com.pms.app.schema.ShawlSizeDto;

import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */


public interface ShawlSizeService {
    List<ShawlSize> getAll();

    ShawlSize add(ShawlSizeDto shawlSizeDto);

    ShawlSize get(Long id);

    ShawlSize edit(Long id, ShawlSizeDto shawlSizeDto);
}
