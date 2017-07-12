package com.pms.app.schema;

import com.mysema.query.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by arjun on 7/11/2017.
 */

@Getter
@Setter
@NoArgsConstructor
public class WeavingLogResource {


    private Long id;
    private String receiptNumber;
    private int orderNo;
    private String locationName;
    private String customerName;
    private String designName;
    private String printName;
    private String colorName;
    private String sizeName;
    private int quantity;
    private String remarks;

    @QueryProjection
    public WeavingLogResource(Long id,
                              String receiptNumber,
                              int orderNo,
                              String locationName,
                              String customerName,
                              String designName,
                              String printName,
                              String colorName,
                              String sizeName,
                              int quantity,
                              String remarks) {
        this.id = id;
        this.receiptNumber = receiptNumber;
        this.orderNo = orderNo;
        this.locationName = locationName;
        this.customerName = customerName;
        this.designName = designName;
        this.printName = printName;
        this.colorName = colorName;
        this.sizeName = sizeName;
        this.quantity = quantity;
        this.remarks = remarks;
    }


}
