package com.pms.app.schema;


import com.mysema.query.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ClothOrderResource {
    private String designName;
    private String sizeName;
    private String color;
    private String colorCode;
    private Long clothCount;
    private String print;
    private Date orderDate;
    private Date deliveryDate;
    private String customerName;
    private Integer orderNo;



    @QueryProjection
    public ClothOrderResource(String designName,
                              String sizeName,
                              String color,
                              String colorCode,
                              Long clothCount,
                              String print,
                               Date orderDate,
                                       Date deliveryDate,
                                       String customerName,
                                       Integer orderNo) {

        this.designName = designName;
        this.sizeName = sizeName;
        this.color = color;
        this.colorCode = colorCode;
        this.clothCount = clothCount;
        this.print = print;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.customerName = customerName;
        this.orderNo = orderNo;
    }
}
