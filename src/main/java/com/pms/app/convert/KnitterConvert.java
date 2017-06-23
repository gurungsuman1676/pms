package com.pms.app.convert;

import com.pms.app.domain.Knitter;
import com.pms.app.schema.KnitterResource;

import java.util.List;

/**
 * Created by arjun on 6/21/2017.
 */
public interface KnitterConvert {
    List<KnitterResource> convert(List<Knitter> knitters);

    KnitterResource convert(Knitter knitter);
}
