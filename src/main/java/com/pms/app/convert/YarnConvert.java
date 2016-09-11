package com.pms.app.convert;

import com.pms.app.domain.Yarns;
import com.pms.app.schema.YarnResource;

import java.util.List;

public interface YarnConvert {
    List<YarnResource> convert(List<Yarns> yarns);
    YarnResource convert(Yarns yarns);


}
