package com.pms.app.convert;

import com.pms.app.domain.Shawl;
import com.pms.app.schema.ShawlResource;

import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */
public interface ShawlConvert {
    List<ShawlResource> convert(List<Shawl> shawls);

    ShawlResource convert(Shawl shawl);
}
