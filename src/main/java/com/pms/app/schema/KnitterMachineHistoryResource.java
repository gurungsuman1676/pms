package com.pms.app.schema;

import lombok.Data;
import lombok.experimental.Builder;

import java.util.Date;

/**
 * Created by arjun on 6/21/2017.
 */

@Data
@Builder
public class KnitterMachineHistoryResource {
    private Long id;
    private Date created;
    private KnitterResource knitter;
    private MachineResource machine;
    private ClothResource cloth;

}
