package com.pms.app.schema;

import com.mysema.query.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by arjun on 6/27/2017.
 */

@Getter
@Setter
@NoArgsConstructor
public class ShawlInventoryDto {

    private int quantity;

    private Long sizeId;

    private Long designId;

    private Long colorId;

    private String location;

    private String receiptNumber;

    @QueryProjection
    public ShawlInventoryDto(int quantity, Long sizeId, Long designId, Long colorId, String location, String receiptNumber) {
        this.quantity = quantity;
        this.sizeId = sizeId;
        this.designId = designId;
        this.colorId = colorId;
        this.location = location;
        this.receiptNumber = receiptNumber;
    }
}
