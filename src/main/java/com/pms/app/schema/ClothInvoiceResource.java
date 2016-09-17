package com.pms.app.schema;

import com.mysema.query.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by arjun on 9/17/16.
 */


@Getter
@Setter
public class ClothInvoiceResource {
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
    private String boxNumber;
    private Double printAmount;
    private String printCurrency;
    private String shippingNumber;
    private Double price;
    private String currency;


    @QueryProjection
    public ClothInvoiceResource(String designName,
                                String sizeName,
                                String color,
                                String colorCode,
                                Long clothCount,
                                String print,
                                Date orderDate,
                                Date deliveryDate,
                                String customerName,
                                Integer orderNo,
                                String boxNumber,
                                Double printAmount,
                                String printCurrency,
                                String shippingNumber,
                                Double price,
                                String currency) {

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

