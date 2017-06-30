package com.pms.app.schema;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Builder;

/**
 * Created by arjun on 6/27/2017.
 */


@Builder
@Setter
@Getter
public class ShawlPropertiesResource {
    private Long id;
    private String name;
    private String value;
}
