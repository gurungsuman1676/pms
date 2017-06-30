package com.pms.app.service;

import com.pms.app.domain.Shawl;
import com.pms.app.schema.ShawlDto;

import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */


public interface ShawlService {
    List<Shawl> getAll();

    Shawl add(ShawlDto shawlDto);

    Shawl get(Long id);

    Shawl edit(Long id, ShawlDto shawlDto);
}
