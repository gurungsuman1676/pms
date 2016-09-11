package com.pms.app.schema;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PrintDto {
    @NotNull
    private String name;
    @NotNull
    private Long sizeId;
    @NotNull
    private Double amount;
    @NotNull
    private Long currencyId;
}
