package com.pms.app.convert.impl;

import com.pms.app.convert.ShawlColorConvert;
import com.pms.app.domain.ShawlColor;
import com.pms.app.schema.ShawlColorResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by arjun on 6/27/2017.
 */

@Component
public class ShawlColorConvertImpl implements ShawlColorConvert {
    @Override
    public List<ShawlColorResource> convert(List<ShawlColor> shawlColors) {
        return shawlColors.stream().map(this::convert).collect(Collectors.toList());

    }

    @Override
    public ShawlColorResource convert(ShawlColor shawlColor) {
        if (shawlColor == null) {
            return null;
        }
        return ShawlColorResource.builder().id(shawlColor.getId()).name(shawlColor.getName()).build();
    }
}
