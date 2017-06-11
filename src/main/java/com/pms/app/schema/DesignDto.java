package com.pms.app.schema;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DesignDto {
    @NotNull
    private String name;
    private Long parentId;
    @NotNull
    private Long customerId;

    private Double gauge;
}
