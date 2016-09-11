package com.pms.app.convert;


import com.pms.app.domain.Clothes;
import com.pms.app.schema.ClothResource;

import java.util.List;

public interface ClothConvert {
    List<ClothResource> convert(List<Clothes> clothes);

    ClothResource convert(Clothes clothes);
}
