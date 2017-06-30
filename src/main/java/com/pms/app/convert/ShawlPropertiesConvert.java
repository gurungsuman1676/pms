package com.pms.app.convert;

import com.pms.app.domain.ShawlProperties;
import com.pms.app.schema.ShawlPropertiesResource;

import java.util.List;

/**
 * Created by arjun on 6/27/2017.
 */
public interface ShawlPropertiesConvert {
    List<ShawlPropertiesResource> convert(List<ShawlProperties> shawlCustomers);

}
