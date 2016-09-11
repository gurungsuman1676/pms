package com.pms.app.schema;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Builder;

@Getter
@Setter
@Builder
public class LocationResource {
    private Long id;
    private String name;
}