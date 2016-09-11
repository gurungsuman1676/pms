package com.pms.app.convert;

import com.pms.app.domain.Designs;
import com.pms.app.schema.DesignResource;

import java.util.List;

public interface DesignConvert {
    List<DesignResource> convert(List<Designs> designs);

    DesignResource convert(Designs designs);

}
