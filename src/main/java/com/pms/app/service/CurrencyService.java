package com.pms.app.service;

import com.pms.app.domain.Currency;
import com.pms.app.schema.CurrencyDto;

import java.util.List;

public interface CurrencyService {
    List<Currency> getCurrency();

    Currency addCurrency(CurrencyDto currencyDto);

    Currency getCurrency(Long id);

    Currency updateCurrency(Long id, CurrencyDto currencyDto);
}
