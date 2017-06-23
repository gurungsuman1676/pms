package com.pms.app.convert.impl;

import com.pms.app.convert.KnitterConvert;
import com.pms.app.domain.Knitter;
import com.pms.app.schema.KnitterResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by arjun on 6/21/2017.
 */

@Component
public class KnitterConvertImpl implements KnitterConvert {
    @Override
    public List<KnitterResource> convert(List<Knitter> knitters) {
        return knitters.stream().map(this::convert).collect(Collectors.toList());

    }

    @Override
    public KnitterResource convert(Knitter knitter) {
        if (knitter == null) {
            return null;
        }
        return KnitterResource.builder().id(knitter.getId()).name(knitter.getName()).build();
    }
}
