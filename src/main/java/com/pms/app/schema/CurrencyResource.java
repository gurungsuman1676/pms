package com.pms.app.schema;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Builder;

@Getter
@Setter
@Builder
public class CurrencyResource {

    private Long id;
    private String name;
}
