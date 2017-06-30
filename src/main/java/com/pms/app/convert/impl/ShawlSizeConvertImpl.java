package com.pms.app.convert.impl;

import com.pms.app.convert.ShawlSizeConvert;
import com.pms.app.domain.ShawlSize;
import com.pms.app.schema.ShawlSizeResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by arjun on 6/27/2017.
 */

@Component
public class ShawlSizeConvertImpl implements ShawlSizeConvert {
    @Override
    public List<ShawlSizeResource> convert(List<ShawlSize> shawlSizes) {
        return shawlSizes.stream().map(this::convert).collect(Collectors.toList());

    }

    @Override
    public ShawlSizeResource convert(ShawlSize shawlSize) {
        if (shawlSize == null) {
            return null;
        }
        return ShawlSizeResource.builder().id(shawlSize.getId()).name(shawlSize.getName()).build();
    }
}
