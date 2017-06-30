package com.pms.app.convert;

import com.pms.app.domain.ShawlSize;
import com.pms.app.schema.ShawlSizeResource;

import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */
public interface ShawlSizeConvert {
    List<ShawlSizeResource> convert(List<ShawlSize> shawlSizes);

    ShawlSizeResource convert(ShawlSize shawlSize);
}
