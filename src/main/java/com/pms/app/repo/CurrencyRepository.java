package com.pms.app.repo;

import com.pms.app.domain.Currency;

public interface CurrencyRepository extends AbstractRepository<Currency> {
    Currency findByName(String name);
}
