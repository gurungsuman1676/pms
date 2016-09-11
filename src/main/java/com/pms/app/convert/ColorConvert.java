package com.pms.app.convert;

import com.pms.app.domain.Colors;
import com.pms.app.schema.ColorResource;

import java.util.List;

public interface ColorConvert {
    List<ColorResource> convert(List<Colors> colors);

    ColorResource convert(Colors colors);
}
