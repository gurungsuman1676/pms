package com.pms.app.schema;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CustomerDto {
    @NotNull
    private String name;
    @NotNull
    private Long currencyId;
    private Long parentId;
}
