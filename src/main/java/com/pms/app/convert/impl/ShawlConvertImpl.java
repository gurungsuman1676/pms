package com.pms.app.convert.impl;

import com.pms.app.convert.ShawlConvert;
import com.pms.app.domain.Shawl;
import com.pms.app.schema.ShawlResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by arjun on 6/27/2017.
 */

@Component
public class ShawlConvertImpl implements ShawlConvert {
    @Override
    public List<ShawlResource> convert(List<Shawl> shawls) {
        return shawls.stream().map(this::convert).collect(Collectors.toList());

    }

    @Override
    public ShawlResource convert(Shawl shawl) {
        if (shawl == null) {
            return null;
        }
        return ShawlResource.builder().id(shawl.getId()).name(shawl.getName()).build();
    }
}
