package com.pms.app.schema;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Builder;

@Getter
@Setter
@Builder
public class CustomerResource {
    private String name;
    private Long currencyId;
    private String currencyName;
    private Long id;
    private Long parentId;
    private String parentName;
}
