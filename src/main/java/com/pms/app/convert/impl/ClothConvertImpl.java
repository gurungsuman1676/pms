package com.pms.app.convert.impl;

import com.pms.app.convert.ClothConvert;
import com.pms.app.convert.CustomerConvert;
import com.pms.app.convert.PriceConvert;
import com.pms.app.convert.PrintConvert;
import com.pms.app.domain.Clothes;
import com.pms.app.schema.ClothResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class ClothConvertImpl implements ClothConvert {
    private final PrintConvert printConvert;
    private final PriceConvert priceConvert;
    private final CustomerConvert customerConvert;
    @Autowired
    public ClothConvertImpl(PrintConvert printConvert, PriceConvert priceConvert, CustomerConvert customerConvert) {
        this.printConvert = printConvert;
        this.priceConvert = priceConvert;
        this.customerConvert = customerConvert;
    }

    @Override
    public List<ClothResource> convert(List<Clothes> clothes) {
        return clothes.stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public ClothResource convert(Clothes clothes) {
        return ClothResource.builder()
                .id(clothes.getId())
                .created(clothes.getCreated())
                .delivery_date(clothes.getDeliver_date())
                .order_no(clothes.getOrder_no())
                .price(priceConvert.convert(clothes.getPrice()))
                .customer(customerConvert.convertCustomer(clothes.getCustomer()))
                .print(clothes.getPrint() != null ? printConvert.convert(clothes.getPrint()) : null)
                .locationName(clothes.getLocation() != null ? clothes.getLocation().getName() : "N/A")
                .printAmount(clothes.getPrint() != null ? clothes.getPrint().getAmount() : 0)
                .clothAmount(clothes.getPrice().getAmount())
                .boxNumber(clothes.getBoxNumber() == null ? "N/A" : clothes.getBoxNumber())
                .shippingNumber(clothes.getShipping() == null ? "N/A" : clothes.getShipping())
                .colorCode(clothes.getColor()!= null ? clothes.getColor().getCode() : "N/A")
                .isReturn(clothes.getIsReturn())
                .weight(clothes.getWeight())
                .build();
    }
}
