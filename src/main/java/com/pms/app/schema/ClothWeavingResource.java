package com.pms.app.schema;

import com.mysema.query.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by arrowhead on 11/22/16.
 */

@Getter
@Setter
public class ClothWeavingResource {
    private String designName;
    private String sizeName;
    private String color;
    private String colorCode;
    private Long clothCount;
    private String print;
    private String customerName;
    private Integer orderNo;
    private Double printAmount;
    private Double amount;



    @QueryProjection
    public ClothWeavingResource(String designName,
                              String sizeName,
                              String color,
                              String colorCode,
                              Long clothCount,
                              String print,
                              String customerName,
                              Integer orderNo,
                                Double printAmount,
                                             Double amount) {

        this.designName = designName;
        this.sizeName = sizeName;
        this.color = color;
        this.colorCode = colorCode;
        this.clothCount = clothCount;
        this.print = print;
        this.customerName = customerName;
        this.orderNo = orderNo;
        this.amount = amount;
        this.printAmount = printAmount;
    }
}
