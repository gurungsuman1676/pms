package com.pms.app.schema;

import lombok.Data;
import lombok.experimental.Builder;

/**
 * Created by arjun on 6/21/2017.
 */

@Data
@Builder
public class MachineResource {
    private Long id;
    private String name;
}
