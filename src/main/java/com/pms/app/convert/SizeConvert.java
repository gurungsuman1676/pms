package com.pms.app.convert;

import com.pms.app.domain.Sizes;
import com.pms.app.schema.SizeResource;

import java.util.List;


public interface SizeConvert {
    List<SizeResource> convertSizes(List<Sizes> sizes);
    SizeResource convertSize(Sizes sizes);

}
