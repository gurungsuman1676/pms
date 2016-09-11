package com.pms.app.service;

import com.pms.app.domain.Prices;
import com.pms.app.schema.PriceDto;

import java.util.List;

public interface PriceService {
    List<Prices> getPrices();

    Prices addPrice(PriceDto priceDto);

    Prices getPrice(Long id);

    Prices updateDesign(Long id, PriceDto priceDto);

    Prices getPriceByDesignAndSizeAndColor(Long designId, Long sizeId, Long colorId);
}
