package com.pms.app.schema;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class ClothDto {
    @NotNull
    private Long designId;
    @NotNull
    private Long sizeId;
    @NotNull
    private Long colorId;
    @NotNull
    private Integer orderNo;
    @NotNull
    private Date delivery_date;
    private Long printId;
    @NotNull
    private  Long customerId;
    @NotNull
    private Integer quantity;

    private Long yarnId;
}
