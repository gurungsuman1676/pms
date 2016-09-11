package com.pms.app.convert;

import com.pms.app.domain.Currency;
import com.pms.app.schema.CurrencyResource;
import com.pms.app.service.CurrencyService;

import java.util.List;

public interface CurrencyConvert {
    CurrencyResource convert(Currency currency);

    List<CurrencyResource> convert(List<Currency> currency);
}
