package com.pms.app.convert.impl;

import com.pms.app.convert.DesignPropertiesConvert;
import com.pms.app.domain.DesignProperties;
import com.pms.app.schema.DesignPropertiesResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by arjun on 6/27/2017.
 */

@Component
public class DesignPropertiesConvertImpl implements DesignPropertiesConvert {

    @Override
    public List<DesignPropertiesResource> convert(List<DesignProperties> designProperties) {
        return designProperties.stream().map(this::convert).collect(Collectors.toList());

    }

    private DesignPropertiesResource convert(DesignProperties designProperties) {
        if (designProperties == null) {
            return null;
        }
        return DesignPropertiesResource.builder().id(designProperties.getId())
                .value(designProperties.getValue())
                .name(designProperties.getName()).build();
    }
}
