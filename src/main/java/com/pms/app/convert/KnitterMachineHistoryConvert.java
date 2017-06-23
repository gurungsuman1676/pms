package com.pms.app.convert;

import com.pms.app.domain.KnitterMachineHistory;
import com.pms.app.schema.KnitterMachineHistoryResource;

import java.util.List;

/**
 * Created by arjun on 6/21/2017.
 */
public interface KnitterMachineHistoryConvert {

    List<KnitterMachineHistoryResource> convert(List<KnitterMachineHistory> knitterMachineHistories);

    KnitterMachineHistoryResource convert(KnitterMachineHistory knitterMachineHistory);
}
