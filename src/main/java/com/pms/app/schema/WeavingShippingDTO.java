package com.pms.app.schema;

import lombok.Data;

import java.util.List;

/**
 * Created by arjun on 6/6/2017.
 */

@Data
public class WeavingShippingDTO {
    private Integer orderNo;
    private String shipping;
    private String boxNumber;
    private Long customerId;
    private Long sizeId;
    private Long designId;
    private Long printId;
    private int quantity;
    private Long colorId;
     private String extraField;
    private Long locationId;
    private String receiptNumber;
    private String remarks;
    private Long docId;
//    private Long colorId;
}
