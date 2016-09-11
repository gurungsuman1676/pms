package com.pms.app.schema;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Builder;

@Builder
@Setter
@Getter
public class SizeResource {
    private String name;
    private Long id;
}
