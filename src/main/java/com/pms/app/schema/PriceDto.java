package com.pms.app.schema;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PriceDto {
    @NotNull
    private Long designId;
    @NotNull
    private Long sizeId;

    private Long yarnId;
    @NotNull
    private Double amount;
}
