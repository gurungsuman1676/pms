package com.pms.app.controller;


import com.pms.app.convert.CurrencyConvert;
import com.pms.app.schema.CurrencyDto;
import com.pms.app.schema.CurrencyResource;
import com.pms.app.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping(value =Routes.CURRENCY)
public class CurrencyController {

    private final CurrencyService currencyService;
    private final CurrencyConvert currencyConvert;

    @Autowired
    public CurrencyController(CurrencyService currencyService, CurrencyConvert currencyConvert) {
        this.currencyService = currencyService;
        this.currencyConvert = currencyConvert;
    }

    @RequestMapping(value="", method = RequestMethod.GET)
    private List<CurrencyResource> getCurrencies(){
        return currencyConvert.convert(currencyService.getCurrency());
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public CurrencyResource addCurrency(@RequestBody CurrencyDto currencyDto){
        return currencyConvert.convert(currencyService.addCurrency(currencyDto));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public CurrencyResource getCurrency(@PathVariable Long id){
        return currencyConvert.convert(currencyService.getCurrency(id));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public CurrencyResource editCurrency(@PathVariable Long id,@RequestBody CurrencyDto currencyDto){
        return currencyConvert.convert(currencyService.updateCurrency(id,currencyDto));
    }

}
