package com.pms.app.schema;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Builder;

@Getter
@Setter
@Builder
public class DesignResource {
    private Long id;
    private String name;
    private Long parentId;
    private String parentName;
    private Long customerId;
    private String customerName;
}
