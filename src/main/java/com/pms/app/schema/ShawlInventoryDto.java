package com.pms.app.schema;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by arjun on 6/27/2017.
 */

@Getter
@Setter
public class ShawlInventoryDto {

    private int quantity;

    private Long sizeId;

    private Long designId;

    private Long colorId;

    private String location;

}
