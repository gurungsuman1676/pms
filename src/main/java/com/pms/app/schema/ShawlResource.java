package com.pms.app.schema;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Builder;

/**
 * Created by arjun on 6/27/2017.
 */

@Getter
@Setter
@Builder
public class ShawlResource {
    private Long id;
    private String name;
}
