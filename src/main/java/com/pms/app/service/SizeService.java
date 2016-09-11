package com.pms.app.service;

import com.pms.app.domain.Sizes;
import com.pms.app.schema.SizeDto;

import java.util.List;


public interface SizeService {
    List<Sizes> getSizes(Long designId, Long yarnId);

    Sizes addSize(SizeDto sizeDto);

    Sizes getSize(Long id);

    Sizes updateSize(Long id, SizeDto sizeDto);
}
