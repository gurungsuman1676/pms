package com.pms.app.schema;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class SizeDto {
    @NotNull
    private String name;

}
