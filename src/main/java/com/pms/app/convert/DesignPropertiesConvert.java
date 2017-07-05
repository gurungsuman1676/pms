package com.pms.app.convert;

import com.pms.app.domain.DesignProperties;
import com.pms.app.schema.DesignPropertiesResource;

import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */
public interface DesignPropertiesConvert {
    List<DesignPropertiesResource> convert(List<DesignProperties> shawlCustomers);

}
