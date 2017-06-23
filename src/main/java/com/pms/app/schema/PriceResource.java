package com.pms.app.schema;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Builder;

@Getter
@Setter
@Builder
public class PriceResource {
    private Long id;
    private Long designId;
    private String designName;
    private Long sizeId;
    private String sizeName;
    private String colorName;
    private Long yarnId;
    private String yarnName;
    private Double amount;
    private CustomerResource customer;
    private Double gauge;
    private String setting;
}
