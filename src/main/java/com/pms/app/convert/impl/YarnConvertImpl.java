package com.pms.app.convert.impl;

import com.pms.app.convert.YarnConvert;
import com.pms.app.domain.Yarns;
import com.pms.app.schema.YarnResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class YarnConvertImpl implements YarnConvert {
    @Override
    public List<YarnResource> convert(List<Yarns> yarns) {
        if(yarns == null ){
            return  null;
        }
        return yarns.stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public YarnResource convert(Yarns yarns) {
        return YarnResource.builder()
                .id(yarns.getId()).name(yarns.getName()).build();
    }
}
