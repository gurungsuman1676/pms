package com.pms.app.convert;

import com.pms.app.domain.Prices;
import com.pms.app.schema.PriceResource;

import java.util.List;

public interface PriceConvert {
    List<PriceResource> convert(List<Prices> prices);

    PriceResource convert(Prices prices);
}
