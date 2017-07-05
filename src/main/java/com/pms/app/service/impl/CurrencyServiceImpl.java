package com.pms.app.service.impl;

import com.pms.app.domain.Currency;
import com.pms.app.repo.CurrencyRepository;
import com.pms.app.schema.CurrencyDto;
import com.pms.app.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {
   private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    @Cacheable(cacheNames = "currencies")
    public List<Currency> getCurrency() {
        return (List<Currency>) currencyRepository.findAll();
    }

    @Override
    @CacheEvict(cacheNames = "currencies",allEntries = true)
    public Currency addCurrency(CurrencyDto currencyDto) {

        Currency currency = new Currency();
        Currency duplicateCurrency = currencyRepository.findByName(currencyDto.getName());
        if(duplicateCurrency != null){
            throw new RuntimeException("Currency name already exists");
        }
        currency.setName(currencyDto.getName());
        return currencyRepository.save(currency);
    }

    @Override
    public Currency getCurrency(Long id) {

        Currency currency = currencyRepository.findOne(id);
        if(currency == null){
            throw new RuntimeException("Currency is not available");
        }
        return currency;
    }

    @Override
    @CacheEvict(cacheNames = "currencies",allEntries = true)
    public Currency updateCurrency(Long id, CurrencyDto currencyDto) {
        Currency currency = currencyRepository.findOne(id);
        if(currency == null){
            throw new RuntimeException("Currency is not available");
        }
        Currency duplicateCurrency = currencyRepository.findByName(currencyDto.getName());
        if(duplicateCurrency != null && currency.getId() != id){
            throw new RuntimeException("Currency name already exists");
        }
        currency.setName(currencyDto.getName());
        return currencyRepository.save(currency);
    }
}
