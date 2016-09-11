package com.pms.app.convert.impl;

import com.pms.app.convert.CurrencyConvert;
import com.pms.app.domain.Currency;
import com.pms.app.schema.CurrencyResource;
import com.pms.app.service.CurrencyService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class CurrencyConvertImpl implements CurrencyConvert {
    @Override
    public CurrencyResource convert(Currency currency) {
        return CurrencyResource.builder().id(currency.getId()).name(currency.getName()).build();
    }

    @Override
    public List<CurrencyResource> convert(List<Currency> currency) {
        if(currency == null || currency.isEmpty()){
            return  new ArrayList<>();
        }
        return currency.stream().map(e -> convert(e)).collect(Collectors.toList());
    }
}
