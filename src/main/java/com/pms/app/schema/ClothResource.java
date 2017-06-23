package com.pms.app.schema;

import com.mysema.query.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Builder;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClothResource {
    private Date created;
    private Long id;
    private PriceResource price;
    private Integer order_no;
    private Date delivery_date;
    private PrintResource print;
    private Double printAmount;
    private Double clothAmount;
    private CustomerResource customer;
    private String locationName;
    private String shippingNumber;
    private String boxNumber;
    private String barcode_url;
    private String colorCode;
    private Boolean isReturn;
    private String weight;
    private Integer type;
    private boolean reOrder;


    @QueryProjection
    public ClothResource(
            Long id,
            String locationName,
            Double price,
            String currency,
            Integer order_no,
            String designName,
            String yarnName,
            String colorCode,
            String size,
            String customerName,
            Date delivery_date,
            String printCurrency,
            Double printAmount,
            String printName,
            String shippingNumber,
            String boxNumber,
            String weight,Boolean isReturn,
            Date created,
            Integer type){

        this.created = created;
        this.id = id;
        this.price = PriceResource.builder()
        .amount(price)
        .customer(CustomerResource.builder()
                .name(customerName)
                .currencyName(currency)
                .build())
                .designName(designName)
                .yarnName(yarnName)
                .sizeName(size)
        .build();
        this.order_no = order_no;
        this.delivery_date = delivery_date;
        this.print = PrintResource.builder().currencyName(printCurrency)
        .amount(printAmount)
                .name(printName)
        .build();
        this.clothAmount = price;
        this.locationName = locationName;
        this.shippingNumber = shippingNumber;
        this.boxNumber = boxNumber;
        this.colorCode = colorCode;
        this.isReturn = isReturn;
        this.weight = weight;
        this.type = type;
    }
}
