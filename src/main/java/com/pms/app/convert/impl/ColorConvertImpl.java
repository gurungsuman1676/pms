package com.pms.app.convert.impl;

import com.pms.app.convert.ColorConvert;
import com.pms.app.domain.Colors;
import com.pms.app.schema.ColorResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class ColorConvertImpl implements ColorConvert {
    @Override
    public List<ColorResource> convert(List<Colors> colors) {
        return colors.stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public ColorResource convert(Colors colors) {
        return ColorResource.builder()
                .id(colors.getId())
                .name_company(colors.getName_company())
                .code(colors.getCode())
                .yarnId( colors.getYarn() != null ? colors.getYarn().getId() : 0L)
                .yarnName( colors.getYarn() !=null? colors.getYarn().getName():"N/A")
                .build();
    }
}
