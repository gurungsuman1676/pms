package com.pms.app.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Builder;

/**
 * Created by arjun on 6/27/2017.
 */


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DesignPropertiesResource {
    private Long id;
    private String name;
    private String value;
}
