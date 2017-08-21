package com.pms.app.schema;


import com.pms.app.domain.LocationType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LocationDto {
    @NotNull
    private String name;

    @NotNull
    private LocationType locationType;

    private int order;
}
