package com.pms.app.schema;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LocationDto {
    @NotNull
    private String name;
}
