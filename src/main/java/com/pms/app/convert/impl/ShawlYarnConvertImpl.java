package com.pms.app.convert.impl;

import com.pms.app.convert.ShawlYarnConvert;
import com.pms.app.domain.ShawlYarn;
import com.pms.app.schema.ShawlYarnResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by arjun on 6/27/2017.
 */

@Component
public class ShawlYarnConvertImpl implements ShawlYarnConvert {
    @Override
    public List<ShawlYarnResource> convert(List<ShawlYarn> shawlYarns) {
        return shawlYarns.stream().map(this::convert).collect(Collectors.toList());

    }

    @Override
    public ShawlYarnResource convert(ShawlYarn shawlYarn) {
        if (shawlYarn == null) {
            return null;
        }
        return ShawlYarnResource.builder().id(shawlYarn.getId()).name(shawlYarn.getName()).build();
    }
}
