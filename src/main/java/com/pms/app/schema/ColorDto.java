package com.pms.app.schema;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ColorDto {
    @NotNull
    private String code;
    @NotNull
    private Long yarnId;
    @NotNull
    private String name_company;
    @NotNull
    private String name_supplier ;

}
