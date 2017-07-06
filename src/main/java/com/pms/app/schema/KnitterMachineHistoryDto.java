package com.pms.app.schema;

import lombok.Data;

/**
 * Created by arjun on 6/21/2017.
 */

@Data
public class KnitterMachineHistoryDto {
    private Long knitterId;
    private Long machineId;
    private Long clothId;
    private int quantity;
}
