package com.pms.app.convert.impl;

import com.pms.app.convert.SizeConvert;
import com.pms.app.domain.Sizes;
import com.pms.app.schema.SizeResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SizeConvertImpl implements SizeConvert {
    @Override
    public List<SizeResource> convertSizes(List<Sizes> sizes) {
        /*
        List<SizeResource> resources = new ArrayList<>();
        for(Sizes size :sizes){
            resources.add(convertSize(size));
        }
        return  resources;
        */
        return sizes.stream().map(this::convertSize).collect(Collectors.toList());
    }

    @Override
    public SizeResource convertSize(Sizes sizes){
        return SizeResource.builder()
                .id(sizes.getId())
                .name(sizes.getName())
                .build();
    }
}
