package com.pms.app.convert;

import com.pms.app.domain.ShawlColor;
import com.pms.app.schema.ShawlColorResource;

import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */
public interface ShawlColorConvert {
    List<ShawlColorResource> convert(List<ShawlColor> shawlColors);

    ShawlColorResource convert(ShawlColor shawlColor);
}
