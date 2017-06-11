package com.pms.app.convert.impl;


import com.pms.app.convert.DesignConvert;
import com.pms.app.domain.Designs;
import com.pms.app.schema.DesignResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DesignConvertImpl implements DesignConvert{
    @Override
    public List<DesignResource> convert(List<Designs> designs) {
        return designs.stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public DesignResource convert(Designs designs) {
        return DesignResource.builder()
                .id(designs.getId())
                .customerName(designs.getCustomer().getName())
                .customerId(designs.getCustomer().getId())
                .parentId(designs.getParent() != null ? designs.getParent().getId() : null)
                .parentName(designs.getParent() != null ? designs.getParent().getName() : null)
                .name(designs.getName())
                .gauge(designs.getGauge())
                .build();
    }
}
