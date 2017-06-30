package com.pms.app.convert.impl;

import com.pms.app.convert.ShawlPropertiesConvert;
import com.pms.app.domain.ShawlProperties;
import com.pms.app.schema.ShawlPropertiesResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by arjun on 6/27/2017.
 */

@Component
public class ShawlPropertiesConvertImpl implements ShawlPropertiesConvert {

    @Override
    public List<ShawlPropertiesResource> convert(List<ShawlProperties> shawlProperties) {
        return shawlProperties.stream().map(this::convert).collect(Collectors.toList());

    }

    private ShawlPropertiesResource convert(ShawlProperties shawlProperties) {
        if (shawlProperties == null) {
            return null;
        }
        return ShawlPropertiesResource.builder().id(shawlProperties.getId())
                .value(shawlProperties.getValue())
                .name(shawlProperties.getName()).build();
    }
}
