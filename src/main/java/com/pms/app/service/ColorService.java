package com.pms.app.service;

import com.pms.app.domain.Colors;
import com.pms.app.schema.ColorDto;

import java.util.List;

public interface ColorService {
    List<Colors> getColors();

    Colors addColor(ColorDto colorDto);

    Colors getColor(Long id);

    Colors editColor(Long id, ColorDto colorDto);

    List<Colors> getColorsByYarnId(Long yarnId);
}
