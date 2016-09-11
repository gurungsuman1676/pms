package com.pms.app.schema;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Builder;

@Getter
@Setter
@Builder
public class ColorResource {
    private Long id;
    private String code;
    private Long yarnId;
    private String name;
    private String yarnName;
    private String customerName;
    private Long customerId;

}
