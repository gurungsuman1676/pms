package com.pms.app.schema;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Builder;

@Getter
@Setter
@Builder
public class PrintResource {
    private Long id;
    private String name;
    private String sizeName;
    private Long sizeId;
    private Double amount;
    private Long currencyId;
    private String currencyName;
}
