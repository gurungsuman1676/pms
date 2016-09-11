package com.pms.app.convert.impl;

import com.pms.app.convert.DesignConvert;
import com.pms.app.convert.PriceConvert;
import com.pms.app.domain.Prices;
import com.pms.app.schema.PriceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PriceConvertImpl implements PriceConvert {

    private final CustomerConvertImpl customerConvert;


    @Autowired
    public PriceConvertImpl(CustomerConvertImpl customerConvert) {
        this.customerConvert = customerConvert;
    }

    @Override
    public List<PriceResource> convert(List<Prices> prices) {
        return prices.stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public PriceResource convert(Prices prices) {
        return PriceResource.builder()
                .id(prices.getId())
                .amount(prices.getAmount())
                .yarnId(prices.getYarn() != null ? prices.getYarn().getId() : 0L)
                .yarnName(prices.getYarn() != null ? prices.getYarn().getName():"N/A")
                .designId(prices.getDesign().getId())
                .designName(prices.getDesign().getName())
                .sizeId(prices.getSize().getId())
                .sizeName(prices.getSize().getName())
                .customer(customerConvert.convertCustomer(prices.getDesign().getCustomer()))
                .build();
    }
}
