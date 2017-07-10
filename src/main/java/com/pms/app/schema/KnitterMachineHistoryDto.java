package com.pms.app.schema;

import com.mysema.query.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.acl.LastOwnerException;

/**
 * Created by arjun on 6/21/2017.
 */

@Data
@NoArgsConstructor
public class KnitterMachineHistoryDto {
    private Long knitterId;
    private Long machineId;
    private Long clothId;
    private int quantity;

    @QueryProjection
    public KnitterMachineHistoryDto(Long knitterId , Long machineId){

        this.knitterId = knitterId;
        this.machineId = machineId;
    }
}
