package com.pms.app.convert;

import com.pms.app.domain.ShawlYarn;
import com.pms.app.schema.ShawlYarnResource;

import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */
public interface ShawlYarnConvert {
    List<ShawlYarnResource> convert(List<ShawlYarn> shawlYarns);

    ShawlYarnResource convert(ShawlYarn shawlYarn);
}
