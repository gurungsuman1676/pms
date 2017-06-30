package com.pms.app.service;

import com.pms.app.domain.ShawlYarn;
import com.pms.app.schema.ShawlYarnDto;

import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */


public interface ShawlYarnService {
    List<ShawlYarn> getAll();

    ShawlYarn add(ShawlYarnDto shawlYarnDto);

    ShawlYarn get(Long id);

    ShawlYarn edit(Long id, ShawlYarnDto shawlYarnDto);
}
